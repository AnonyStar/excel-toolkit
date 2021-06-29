package org.toolkit.easyexcel.read;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionTemplate;
import org.toolkit.easyexcel.read.context.SpringContextHolder;
import org.toolkit.exception.ErrorInfo;
import org.toolkit.exception.ExcelKitException;

/**
 * @author: zhoucx
 * @time: 2021-06-18
 */
public class SpringTransactionExecutor implements ITransactionExecutor {

    private static Logger logger = LoggerFactory.getLogger(SpringTransactionExecutor.class);

    public TransactionTemplate transactionTemplate;

    public SpringTransactionExecutor() {
        this.transactionTemplate = SpringContextHolder.getBean(TransactionTemplate.class);
    }

    @Override
    public void execute(ITransactionRunnable runnable) {
        transactionTemplate.execute((transactionStatus) -> {
            try {
                runnable.run();
            } catch (IllegalAccessException e) {
                logger.error("事务异常：{}", e);
                throw new ExcelKitException(ErrorInfo.HANDLER_EXCEPTION);
            }
            return null;
        });
    }
}
