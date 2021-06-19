package org.toolkit.easyexcel.read;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.toolkit.exception.ExcelKitException;

import java.util.Map;

/**
 * @author: zhoucx
 * @time: 2021-06-18
 */
public class ExcelReadAnalysisListener<T> extends AnalysisEventListener<T> {

    private ITransactionExecutor transactionExecutor;

    private ReadProcessHandler readProcessHandler;

    public ExcelReadAnalysisListener(ITransactionExecutor transactionExecutor,
                                     ReadProcessHandler readProcessHandler) {
        this.transactionExecutor = transactionExecutor;
        this.readProcessHandler = readProcessHandler;
    }

    @Override
    public void invoke(T data, AnalysisContext analysisContext) {
        transactionExecutor.execute(() -> {
            try {
                RowReadStatus readStatus = ExcelValiHelper.validate(data);
                if (!readStatus.isStatus()) {
                    // todo 错误记录

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

        super.onException(exception, context);
    }
}
