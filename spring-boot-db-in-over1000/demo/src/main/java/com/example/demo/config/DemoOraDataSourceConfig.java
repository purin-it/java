package com.example.demo.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"com.example.demo.mapper.ora"}, sqlSessionFactoryRef = "sqlSessionFactoryOra")
public class DemoOraDataSourceConfig {

    /**
     * Oracleのデータソースプロパティを生成する
     * @return Oracleのデータソースプロパティ
     */
    @Bean(name = {"datasourceOraProperties"})
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties datasourceOraProperties() {
        return new DataSourceProperties();
    }

    /**
     * Oracleのデータソースを生成する
     * @param properties Oracleのデータソースプロパティ
     * @return Oracleのデータソース
     */
    @Bean(name = {"dataSourceOra"})
    @Primary
    public DataSource datasourceOra(@Qualifier("datasourceOraProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    /**
     * Oracleのトランザクションマネージャを生成する
     * @param dataSourceOra Oracleのデータソース
     * @return Oracleのトランザクションマネージャ
     */
    @Bean(name = {"txManagerOra"})
    @Primary
    public PlatformTransactionManager txManagerOra(@Qualifier("dataSourceOra") DataSource dataSourceOra) {
        return new DataSourceTransactionManager(dataSourceOra);
    }

    /**
     * OracleのSQLセッションファクトリを生成する
     * @param dataSourceOra Oracleのデータソース
     * @return OracleのSQLセッションファクトリ
     * @throws Exception 任意例外
     */
    @Bean(name = {"sqlSessionFactoryOra"})
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSourceOra") DataSource dataSourceOra)
            throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSourceOra);
        return sqlSessionFactory.getObject();
    }
}
