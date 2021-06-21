package org.toolkit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.support.TransactionTemplate;
import org.toolkit.easyexcel.read.ITransactionExecutor;
import org.toolkit.easyexcel.read.SpringTransactionExecutor;

@SpringBootApplication
@Configuration
public class GeneratorApp {

    /**
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(GeneratorApp.class, args);
    }
/*    @Bean
    @ConditionalOnMissingBean(ITransactionExecutor.class)
    public ITransactionExecutor transactionExecutor(TransactionTemplate transactionTemplate) {
        return new SpringTransactionExecutor();
    }*/
}