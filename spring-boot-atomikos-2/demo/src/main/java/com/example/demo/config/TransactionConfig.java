package com.example.demo.config;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.jta.JtaTransactionManager;
import javax.transaction.UserTransaction;

//分散トランザクション管理を行うためのオブジェクトを生成する
@Configuration
public class TransactionConfig {

    /**
     * トランザクション管理を行うJtaTransactionManagerを生成
     * @return JtaTransactionManagerオブジェクト
     */
    @Bean
    @Primary
    public JtaTransactionManager regTransactionManager () {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        UserTransaction userTransaction = new UserTransactionImp();
        return new JtaTransactionManager(userTransaction, userTransactionManager);
    }
}
