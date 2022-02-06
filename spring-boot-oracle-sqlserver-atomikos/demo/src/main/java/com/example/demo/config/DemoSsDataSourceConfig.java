package com.example.demo.config;

import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import com.microsoft.sqlserver.jdbc.SQLServerXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"com.example.demo.mapper.ss"}, sqlSessionFactoryRef = "sqlSessionFactorySs")
public class DemoSsDataSourceConfig {

    /**
     * SQL Serverのデータソースプロパティを生成する
     * @return SQL Serverのデータソースプロパティ
     */
    @Bean(name = {"datasourceSsProperties"})
    @ConfigurationProperties(prefix = "spring.datasourcess")
    public DataSourceProperties datasourceSsProperties() {
        return new DataSourceProperties();
    }

    /**
     * SQL Serverのデータソースを生成する
     * @param properties SQL Serverのデータソースプロパティ
     * @return SQL Serverのデータソース
     */
    @Bean(name = {"dataSourceSs"})
    public DataSource datasourceSs(
            @Qualifier("datasourceSsProperties") DataSourceProperties properties) {
        // SQLServerXAデータソースオブジェクトを作成
        SQLServerXADataSource xaDataSource = new SQLServerXADataSource();
        // URL・ユーザー名・パスワードを引数の定義ファイルから取得し設定
        xaDataSource.setURL(properties.getUrl());
        xaDataSource.setUser(properties.getUsername());
        xaDataSource.setPassword(properties.getPassword());
        // AtomikosデータソースBeanオブジェクトを生成
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        // 一意なリソース名・SQLServerXAデータソースオブジェクト・コネクションプールサイズを設定し返却
        atomikosDataSourceBean.setUniqueResourceName("atomikosDataSourceSs");
        atomikosDataSourceBean.setXaDataSource(xaDataSource);
        atomikosDataSourceBean.setPoolSize(5);
        return atomikosDataSourceBean;
    }

    /**
     * SQL Serverのトランザクションマネージャを生成する
     * @param dataSourceSs SQL Serverのデータソース
     * @return SQL Serverのトランザクションマネージャ
     */
    @Bean(name = {"txManagerSs"})
    public PlatformTransactionManager txManagerSs(@Qualifier("dataSourceSs") DataSource dataSourceSs) {
        return new DataSourceTransactionManager(dataSourceSs);
    }

    /**
     * SQL ServerのSQLセッションファクトリを生成する
     * @param dataSourceSs SQL Serverのデータソース
     * @return SQL ServerのSQLセッションファクトリを生成する
     * @throws Exception 任意例外
     */
    @Bean(name = {"sqlSessionFactorySs"})
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSourceSs") DataSource dataSourceSs)
            throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSourceSs);
        return sqlSessionFactory.getObject();
    }
}
