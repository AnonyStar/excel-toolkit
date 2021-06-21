package org.toolkit;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;
import org.toolkit.easyexcel.read.ITransactionExecutor;
import org.toolkit.easyexcel.read.SpringTransactionExecutor;

/**
 * @author: zhoucx
 * @time: 2021-06-21
 */
@Configuration
public class SpringConfig {

/*
    @Bean
    @ConditionalOnMissingBean(ITransactionExecutor.class)
    public ITransactionExecutor transactionExecutor(TransactionTemplate transactionTemplate) {
        return new SpringTransactionExecutor(transactionTemplate);
    }*/
   /* @Bean
    public TransactionTemplate transactionTemplate() {
        return new TransactionTemplate();
    }*/

}
