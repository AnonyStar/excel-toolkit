package org.toolkit.easyexcel.read;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Objects;


/**
 * @author: zhoucx
 * @time: 2021-06-18
 */
public class ExcelReadAnalysisListener<T> extends AnalysisEventListener<T> {

    private ITransactionExecutor transactionExecutor;

    private ReadProcessHandler readProcessHandler;

    private ExceptionProcessHandler exceptionProcessHandler;

    public ExcelReadAnalysisListener(ITransactionExecutor transactionExecutor,
                                     ReadProcessHandler readProcessHandler,
                                     ExceptionProcessHandler exceptionProcessHandler) {
        this.transactionExecutor = transactionExecutor;
        this.readProcessHandler = readProcessHandler;
        this.exceptionProcessHandler = exceptionProcessHandler;
    }

    @Override
    public void invoke(T data, AnalysisContext analysisContext) {

        transactionExecutor.execute(() -> {
            try {
                RowReadStatus readStatus = ExcelValiHelper.validate(data);
                if (Objects.nonNull(readStatus) && !readStatus.isStatus()) {
                    // todo 错误记录 待实现
                   // System.out.println("校验信息：" + readStatus);
                    readStatus.setHeadClass(data.getClass());

                    exceptionProcessHandler.handlerException(data);
                } else {
                    readProcessHandler.doprocess(data,analysisContext);
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }

        });
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        RowReadStatus readStatus = new RowReadStatus();
        exceptionProcessHandler.handlerException(readStatus);
        Integer rowIndex = context.readRowHolder().getRowIndex();
        // todo 读取异常
        System.out.println("读取数据异常：" + context.readRowHolder().getRowIndex());
        System.out.println("具体异常：" + exception.getMessage());
    }
}
