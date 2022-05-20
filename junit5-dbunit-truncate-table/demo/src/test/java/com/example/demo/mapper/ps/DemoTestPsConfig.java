package com.example.demo.mapper.ps;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

// MyBatisのテストを実行する際のDB接続定義
// @MybatisTestアノテーションでテストを実行する際、
// Spring Bootアプリケーションが起動するようにする。
// その際、com.example.demo.mapper.oraフォルダ下のインスタンス
// (UserDataMapper)のみがDIで使用できるようになる。
@SpringBootApplication
@PropertySource(value = {"classpath:test.properties"})
public class DemoTestPsConfig {

    /**
     * PostgreSQLのデータソースプロパティ(UT)を生成する.
     * @return PostgreSQLのデータソースプロパティ(UT)
     */
    @Bean(name = {"datasourcePsProperties"})
    @Primary
    @ConfigurationProperties(prefix = "spring.datasourceut-ps")
    public DataSourceProperties datasourcePsProperties() {
        return new DataSourceProperties();
    }

    /**
     * PostgreSQLのデータソース(UT)を生成する.
     * @param properties PostgreSQLのデータソースプロパティ(UT)
     * @return PostgreSQLのデータソース(UT)
     */
    @Bean(name = {"dataSourcePs"})
    @Primary
    public DataSource datasourcePs(@Qualifier("datasourcePsProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    /**
     * PostgreSQLのトランザクションマネージャ(UT)を生成する.
     * @param dataSourcePs PostgreSQLのデータソース(UT)
     * @return PostgreSQLのトランザクションマネージャ(UT)
     */
    @Bean(name = {"txManagerPs"})
    @Primary
    public PlatformTransactionManager txManagerPs(@Qualifier("dataSourcePs") DataSource dataSourcePs) {
        return new DataSourceTransactionManager(dataSourcePs);
    }

    /**
     * PostgreSQLのDB接続設定(UT)を生成する.
     * @return PostgreSQLのDB接続設定(UT)
     */
    @Bean(name = {"dbUnitDatabaseConfigPs"})
    public DatabaseConfigBean dbUnitDatabaseConfigPs() {
        DatabaseConfigBean bean = new DatabaseConfigBean();
        bean.setAllowEmptyFields(true);
        bean.setDatatypeFactory(new PostgresqlDataTypeFactory());
        return bean;
    }

    /**
     * PostgreSQLのデータソースコネクションファクトリ(UT)を生成する.
     * @param dbUnitDatabaseConfigPs PostgreSQLのDB接続設定(UT)
     * @param dataSourcePs           PostgreSQLのデータソース(UT)
     * @return PostgreSQLのデータソースコネクションファクトリ(UT)
     */
    @Bean(name = {"dbUnitDatabaseConnectionPs"})
    public DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnectionPs(
            @Qualifier("dbUnitDatabaseConfigPs") DatabaseConfigBean dbUnitDatabaseConfigPs,
            @Qualifier("dataSourcePs") DataSource dataSourcePs) {
        DatabaseDataSourceConnectionFactoryBean bean = new DatabaseDataSourceConnectionFactoryBean(dataSourcePs);
        bean.setDatabaseConfig(dbUnitDatabaseConfigPs);
        return bean;
    }
}
