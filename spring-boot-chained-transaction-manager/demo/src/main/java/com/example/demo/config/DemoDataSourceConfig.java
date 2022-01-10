package com.example.demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DemoDataSourceConfig {

    /**
     * 複数トランザクションマネージャを1つにまとめて定義する
     * @param txManagerOra Oracleのトランザクションマネージャ
     * @param txManagerSs SQL Serverのトランザクションマネージャ
     * @return 複数トランザクションマネージャを1つにまとめて定義したChainedTransactionManager
     */
    @Bean(name = "txManagerChained")
    public ChainedTransactionManager transactionManager(
            @Qualifier("txManagerOra") PlatformTransactionManager txManagerOra,
            @Qualifier("txManagerSs") PlatformTransactionManager txManagerSs) {
        return new ChainedTransactionManager(txManagerOra, txManagerSs);
    }

}
