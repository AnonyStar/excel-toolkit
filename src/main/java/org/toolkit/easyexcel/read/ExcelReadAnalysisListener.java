package org.toolkit.easyexcel.read;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toolkit.easyexcel.read.context.ReadContext;
import org.toolkit.easyexcel.read.context.ReadContextHolder;
import org.toolkit.exception.ErrorInfo;
import org.toolkit.exception.ExcelKitException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;


/**
 * @author: zhoucx
 * @time: 2021-06-18
 */
public class ExcelReadAnalysisListener<T> extends AnalysisEventListener<T> {

    private static Logger logger = LoggerFactory.getLogger(ExcelReadAnalysisListener.class);


    private ITransactionExecutor transactionExecutor;

    private ReadProcessHandler readProcessHandler;

    private ReadContext readContext;

    private List<T> dataList = new ArrayList<>();

    private ExcelWriter excelWriter;
    private WriteSheet writeSheet;


    public ExcelReadAnalysisListener(ITransactionExecutor transactionExecutor,
                                     ReadProcessHandler readProcessHandler,
                                     ReadContext readContext, ExcelWriter excelWriter) {
        this.transactionExecutor = transactionExecutor;
        this.readProcessHandler = readProcessHandler;
        this.readContext = readContext;
        this.excelWriter = excelWriter;
        this.writeSheet = EasyExcel.writerSheet().build();
        readContext.setReadSheetStatus(RowReadStatus.Status.READING,null);
    }


    @Override
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {

        super.invokeHead(headMap, context);
    }

    @Override
    public void invoke(T data, AnalysisContext analysisContext) {
        AtomicReference<Field> message = new AtomicReference<>();
        AtomicReference<Field> status = new AtomicReference<>();
        transactionExecutor.execute(() -> {
            try {
                ExcelHelper.firstMainlyHandler(data);
                RowReadStatus readStatus = ExcelHelper.validate(data);
                message.set(data.getClass().getSuperclass().getDeclaredField("message"));
                status.set(data.getClass().getSuperclass().getDeclaredField("status"));
                message.get().setAccessible(true);
                status.get().setAccessible(true);
                if (Objects.nonNull(readStatus) && readStatus.isStatus()) {
                    readProcessHandler.doprocess(data, analysisContext);
                    status.get().set(data, RowReadStatus.Status.FINISH.getStatus());
                    readContext.setCurrentRowstatus(RowReadStatus.Status.FINISH);
                } else {
                    message.get().set(data, readStatus.getMessage());
                    status.get().set(data, RowReadStatus.Status.UNFINISHED.getStatus());
                    readContext.setCurrentRowstatus(RowReadStatus.Status.UNFINISHED);
                }

            } catch (Exception e) {
                if (Objects.nonNull(message.get()) && Objects.nonNull(status.get())) {
                    message.get().set(data, "数据处理异常！");
                    status.get().set(data, RowReadStatus.Status.UNFINISHED.getStatus());
                    readContext.setCurrentRowstatus(RowReadStatus.Status.UNFINISHED);
                } else {
                    throw new ExcelKitException(ErrorInfo.HANDLER_EXCEPTION);
                }

            } finally {
                readContext.setReadIndex(analysisContext.readRowHolder().getRowIndex());
                readContext.setSheetCounts(analysisContext.readSheetHolder().getTotal());
                ReadContextHolder.refreshReadContext(readContext);
                logger.debug("处理数据：第{}行数据 ：{}",analysisContext.readRowHolder().getRowIndex(), data);
                dataList.add(data);
                if (dataList.size() > 1000) {
                    excelWriter.write(dataList, writeSheet);
                    dataList.clear();
                }
            }
        });
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        excelWriter.write(dataList, writeSheet);
        dataList.clear();
        excelWriter.finish();
        ExcelHelper.removeFirstMainlyCache();
        readContext.setReadSheetStatus(RowReadStatus.Status.FINISH, null);
        ReadContextHolder.refreshReadContext(readContext);
        //readContext.getFileSystem().removeSourcess();
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        logger.debug("处理异常：第{}行,错误信息：{}", context.readRowHolder().getRowIndex(), exception.getMessage());
        logger.error("错误异常：{}",exception);
    }
}
