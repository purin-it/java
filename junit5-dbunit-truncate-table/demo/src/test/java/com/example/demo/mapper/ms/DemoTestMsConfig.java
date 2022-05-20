package com.example.demo.mapper.ms;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
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
// その際、com.example.demo.mapper.msフォルダ下のインスタンス
// (UserDataMapper)のみがDIで使用できるようになる。
@SpringBootApplication
@PropertySource(value = {"classpath:test.properties"})
public class DemoTestMsConfig {

    /**
     * MySQLのデータソースプロパティ(UT)を生成する.
     * @return MySQLのデータソースプロパティ(UT)
     */
    @Bean(name = {"datasourceMsProperties"})
    @Primary
    @ConfigurationProperties(prefix = "spring.datasourceut-ms")
    public DataSourceProperties datasourceMsProperties() {
        return new DataSourceProperties();
    }

    /**
     * MySQLのデータソース(UT)を生成する.
     * @param properties MySQLのデータソースプロパティ(UT)
     * @return MySQLのデータソース(UT)
     */
    @Bean(name = {"dataSourceMs"})
    @Primary
    public DataSource datasourceMs(@Qualifier("datasourceMsProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    /**
     * MySQLのトランザクションマネージャ(UT)を生成する.
     * @param dataSourceMs MySQLのデータソース(UT)
     * @return MySQLのトランザクションマネージャ(UT)
     */
    @Bean(name = {"txManagerMs"})
    @Primary
    public PlatformTransactionManager txManagerMs(@Qualifier("dataSourceMs") DataSource dataSourceMs) {
        return new DataSourceTransactionManager(dataSourceMs);
    }

    /**
     * MySQLのDB接続設定(UT)を生成する.
     * @return MySQLのDB接続設定(UT)
     */
    @Bean(name = {"dbUnitDatabaseConfigMs"})
    public DatabaseConfigBean dbUnitDatabaseConfigMs() {
        DatabaseConfigBean bean = new DatabaseConfigBean();
        bean.setAllowEmptyFields(true);
        bean.setDatatypeFactory(new MySqlDataTypeFactory());
        return bean;
    }

    /**
     * MySQLのデータソースコネクションファクトリ(UT)を生成する.
     * @param dbUnitDatabaseConfigMs MySQLのDB接続設定(UT)
     * @param dataSourceMs           MySQLのデータソース(UT)
     * @return MySQLのデータソースコネクションファクトリ(UT)
     */
    @Bean(name = {"dbUnitDatabaseConnectionMs"})
    public DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnectionOra(
            @Qualifier("dbUnitDatabaseConfigMs") DatabaseConfigBean dbUnitDatabaseConfigMs,
            @Qualifier("dataSourceMs") DataSource dataSourceMs) {
        DatabaseDataSourceConnectionFactoryBean bean = new DatabaseDataSourceConnectionFactoryBean(dataSourceMs);
        bean.setDatabaseConfig(dbUnitDatabaseConfigMs);
        return bean;
    }
}
