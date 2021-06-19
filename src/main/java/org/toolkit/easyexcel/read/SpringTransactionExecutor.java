package org.toolkit.easyexcel.read;

import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author: zhoucx
 * @time: 2021-06-18
 */
public class SpringTransactionExecutor implements ITransactionExecutor {

    public TransactionTemplate transactionTemplate;

    public SpringTransactionExecutor(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public void execute(ITransactionRunnable runnable) {
        transactionTemplate.execute((transactionStatus) -> {
           runnable.run();
           return null;
        });
    }
}
