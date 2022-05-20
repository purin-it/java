package com.example.demo.mapper.ss;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;
import org.dbunit.ext.mssql.MsSqlDataTypeFactory;
import org.dbunit.ext.oracle.Oracle10DataTypeFactory;
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
public class DemoTestSsConfig {

    /**
     * SQLServerのデータソースプロパティ(UT)を生成する.
     * @return SQLServerのデータソースプロパティ(UT)
     */
    @Bean(name = {"datasourceSsProperties"})
    @Primary
    @ConfigurationProperties(prefix = "spring.datasourceut-ss")
    public DataSourceProperties datasourceSsProperties() {
        return new DataSourceProperties();
    }

    /**
     * SQLServerのデータソース(UT)を生成する.
     * @param properties SQLServerのデータソースプロパティ(UT)
     * @return SQLServerのデータソース(UT)
     */
    @Bean(name = {"dataSourceSs"})
    @Primary
    public DataSource datasourceSs(@Qualifier("datasourceSsProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    /**
     * SQLServerのトランザクションマネージャ(UT)を生成する.
     * @param dataSourceSs SQLServerのデータソース(UT)
     * @return SQLServerのトランザクションマネージャ(UT)
     */
    @Bean(name = {"txManagerSs"})
    @Primary
    public PlatformTransactionManager txManagerSs(@Qualifier("dataSourceSs") DataSource dataSourceSs) {
        return new DataSourceTransactionManager(dataSourceSs);
    }

    /**
     * SQLServerのDB接続設定(UT)を生成する.
     * @return SQLServerのDB接続設定(UT)
     */
    @Bean(name = {"dbUnitDatabaseConfigSs"})
    public DatabaseConfigBean dbUnitDatabaseConfigSs() {
        DatabaseConfigBean bean = new DatabaseConfigBean();
        bean.setAllowEmptyFields(true);
        bean.setDatatypeFactory(new MsSqlDataTypeFactory());
        return bean;
    }

    /**
     * SQLServerのデータソースコネクションファクトリ(UT)を生成する.
     * @param dbUnitDatabaseConfigSs SQLServerのDB接続設定(UT)
     * @param dataSourceSs           SQLServerのデータソース(UT)
     * @return SQLServerのデータソースコネクションファクトリ(UT)
     */
    @Bean(name = {"dbUnitDatabaseConnectionSs"})
    public DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnectionSs(
            @Qualifier("dbUnitDatabaseConfigSs") DatabaseConfigBean dbUnitDatabaseConfigSs,
            @Qualifier("dataSourceSs") DataSource dataSourceSs) {
        DatabaseDataSourceConnectionFactoryBean bean = new DatabaseDataSourceConnectionFactoryBean(dataSourceSs);
        bean.setDatabaseConfig(dbUnitDatabaseConfigSs);
        return bean;
    }
}
