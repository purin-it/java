package com.example.demo.config;

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
@MapperScan(basePackages = {"com.example.demo.mapper.ps"}, sqlSessionFactoryRef = "sqlSessionFactoryPs")
public class DemoPsDataSourceConfig {

    /**
     * PostgreSQLのデータソースプロパティを生成する
     * @return PostgreSQLのデータソースプロパティ
     */
    @Bean(name = {"datasourcePsProperties"})
    @ConfigurationProperties(prefix = "spring.datasourceps")
    public DataSourceProperties datasourcePsProperties() {
        return new DataSourceProperties();
    }

    /**
     * PostgreSQLのデータソースを生成する
     * @param properties PostgreSQLのデータソースプロパティ
     * @return PostgreSQLのデータソース
     */
    @Bean(name = {"dataSourcePs"})
    public DataSource datasourcePs(@Qualifier("datasourcePsProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    /**
     * PostgreSQLのトランザクションマネージャを生成する
     * @param dataSourcePs 　PostgreSQLのデータソース
     * @return PostgreSQLのトランザクションマネージャ
     */
    @Bean(name = {"txManagerPs"})
    public PlatformTransactionManager txManagerPs(@Qualifier("dataSourcePs") DataSource dataSourcePs) {
        return new DataSourceTransactionManager(dataSourcePs);
    }

    /**
     * PostgreSQLのSQLセッションファクトリを生成する
     * @param dataSourcePs PostgreSQLのデータソース
     * @return PostgreSQLのSQLセッションファクトリ
     * @throws Exception 任意例外
     */
    @Bean(name = {"sqlSessionFactoryPs"})
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSourcePs") DataSource dataSourcePs)
            throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSourcePs);
        return sqlSessionFactory.getObject();
    }
}
