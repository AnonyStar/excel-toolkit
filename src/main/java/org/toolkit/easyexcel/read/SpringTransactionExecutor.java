package org.toolkit.easyexcel.read;

import org.springframework.transaction.support.TransactionTemplate;
import org.toolkit.easyexcel.read.context.SpringContextHolder;

/**
 * @author: zhoucx
 * @time: 2021-06-18
 */
public class SpringTransactionExecutor implements ITransactionExecutor {

    public TransactionTemplate transactionTemplate;

    public SpringTransactionExecutor() {
        this.transactionTemplate = SpringContextHolder.getBean(TransactionTemplate.class);
    }

    @Override
    public void execute(ITransactionRunnable runnable) {
        transactionTemplate.execute((transactionStatus) -> {
           runnable.run();
           return null;
        });
    }
}
