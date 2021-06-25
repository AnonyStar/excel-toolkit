package org.toolkit.easyexcel.read;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.toolkit.SpringConfig;
import org.toolkit.easyexcel.read.context.ReadContext;
import org.toolkit.exception.ExcelKitException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @author: zhoucx
 * @time: 2021-06-18
 */
public class ExcelReadAnalysisListener<T> extends AnalysisEventListener<T> {

    private ITransactionExecutor transactionExecutor;

    private ReadProcessHandler readProcessHandler;

    private ReadContext readContext;

    private List<T>  dataList = new ArrayList<>();

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

    }


    @Override
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {

        super.invokeHead(headMap, context);
    }

    @Override
    public void invoke(T data, AnalysisContext analysisContext) {
        transactionExecutor.execute(() -> {
            try {
                RowReadStatus readStatus = ExcelValiHelper.validate(data);
                Field message = data.getClass().getSuperclass().getDeclaredField("message");
                Field status = data.getClass().getSuperclass().getDeclaredField("status");
                message.setAccessible(true);
                status.setAccessible(true);
                if (Objects.nonNull(readStatus) && !readStatus.isStatus()) {
                    // todo 错误记录 待实现
                    message.set(data,readStatus.getMessage());
                    status.set(data, "数据校验错误");
                    throw new ExcelKitException(readStatus.getMessage());
                } else {
                    status.set(data, "已处理");
                    readProcessHandler.doprocess(data,analysisContext);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Field message = null;
                try {
                    message = data.getClass().getSuperclass().getDeclaredField("message");
                    Field status = data.getClass().getSuperclass().getDeclaredField("status");
                    message.setAccessible(true);
                    status.setAccessible(true);
                    if (StringUtils.isEmpty(message.get(data))){
                        message.set(data,"业务处理失败");
                    }
                    if (StringUtils.isEmpty(status.get(data))){
                        status.set(data, "处理错误");
                    }
                } catch (NoSuchFieldException | IllegalAccessException noSuchFieldException) {
                    noSuchFieldException.printStackTrace();
                } finally {
                    throw new ExcelKitException();
                }
            } finally {
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
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {

        // todo 读取异常
        //System.out.println("读取数据异常：" + context.readRowHolder().getRowIndex());
        System.out.println("具体异常：" + exception.getMessage());
    }
}
