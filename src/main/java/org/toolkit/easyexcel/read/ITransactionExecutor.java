package org.toolkit.easyexcel.read;

/**
 * @author: zhoucx
 * @time: 2021-06-18
 */
@FunctionalInterface
public interface ITransactionExecutor {

    void execute(ITransactionRunnable runnable);
}
