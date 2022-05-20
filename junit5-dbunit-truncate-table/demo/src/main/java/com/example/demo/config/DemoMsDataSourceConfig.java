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
@MapperScan(basePackages = {"com.example.demo.mapper.ms"}, sqlSessionFactoryRef = "sqlSessionFactoryMs")
public class DemoMsDataSourceConfig {

    /**
     * MySQLのデータソースプロパティを生成する
     * @return MySQLのデータソースプロパティ
     */
    @Bean(name = {"datasourceMsProperties"})
    @ConfigurationProperties(prefix = "spring.datasourcems")
    public DataSourceProperties datasourceSqlProperties() {
        return new DataSourceProperties();
    }

    /**
     * MySQLのデータソースを生成する
     * @param properties MySQLのデータソースプロパティ
     * @return MySQLのデータソース
     */
    @Bean(name = {"dataSourceMs"})
    public DataSource datasourceMs(@Qualifier("datasourceMsProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    /**
     * MySQLのトランザクションマネージャを生成する
     * @param dataSourceMs MySQLのデータソース
     * @return MySQLのトランザクションマネージャ
     */
    @Bean(name = {"txManagerMs"})
    public PlatformTransactionManager txManagerSql(@Qualifier("dataSourceMs") DataSource dataSourceMs) {
        return new DataSourceTransactionManager(dataSourceMs);
    }

    /**
     * MySQLのSQLセッションファクトリを生成する
     * @param dataSourceMs 　MySQLのデータソース
     * @return MySQLのSQLセッションファクトリ
     * @throws Exception 任意例外
     */
    @Bean(name = {"sqlSessionFactoryMs"})
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSourceMs") DataSource dataSourceMs)
            throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSourceMs);
        return sqlSessionFactory.getObject();
    }
}
