package com.example.demo.mapper;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;
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

// JUnit4ベースでMyBatisのテストを実行する際のDB接続定義

// @MybatisTestアノテーションでテストを実行する際、
// Spring Bootアプリケーションが起動するようにする。

// その際、com.example.demo.mapperフォルダ下のインスタンス
// (UserDataMapper)のみがDIで使用できるようになる。
@SpringBootApplication
@PropertySource(value = {"classpath:test.properties"})
public class DemoTestDbConfig {

    /**
     * Oracleのデータソースプロパティ(UT)を生成する.
     * @return Oracleのデータソースプロパティ(UT)
     */
    @Bean(name = {"datasourceOraProperties"})
    @Primary
    @ConfigurationProperties(prefix = "spring.datasourceut")
    public DataSourceProperties datasourceOraProperties() {
        return new DataSourceProperties();
    }

    /**
     * Oracleのデータソース(UT)を生成する.
     * @param properties Oracleのデータソースプロパティ(UT)
     * @return Oracleのデータソース(UT)
     */
    @Bean(name = {"dataSourceOra"})
    @Primary
    public DataSource datasourceOra(@Qualifier("datasourceOraProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    /**
     * Oracleのトランザクションマネージャ(UT)を生成する.
     * @param dataSourceOra Oracleのデータソース(UT)
     * @return Oracleのトランザクションマネージャ(UT)
     */
    @Bean(name = {"txManagerOra"})
    @Primary
    public PlatformTransactionManager txManagerOra(@Qualifier("dataSourceOra") DataSource dataSourceOra) {
        return new DataSourceTransactionManager(dataSourceOra);
    }

    /**
     * DB接続設定(UT)を生成する.
     * @return DB接続設定(UT)
     */
    @Bean(name = {"dbUnitDatabaseConfig"})
    public DatabaseConfigBean dbUnitDatabaseConfig() {
        DatabaseConfigBean bean = new DatabaseConfigBean();
        bean.setAllowEmptyFields(true);
        bean.setDatatypeFactory(new Oracle10DataTypeFactory());
        return bean;
    }

    /**
     * データソースコネクションファクトリ(UT)を生成する.
     * @param dbUnitDatabaseConfig DB接続設定(UT)
     * @param dataSourceOra        　Oracleのデータソース(UT)
     * @return データソースコネクションファクトリ(UT)
     */
    @Bean(name = {"dbUnitDatabaseConnection"})
    public DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection(
            @Qualifier("dbUnitDatabaseConfig") DatabaseConfigBean dbUnitDatabaseConfig,
            @Qualifier("dataSourceOra") DataSource dataSourceOra) {
        DatabaseDataSourceConnectionFactoryBean bean = new DatabaseDataSourceConnectionFactoryBean(dataSourceOra);
        bean.setDatabaseConfig(dbUnitDatabaseConfig);
        bean.setSchema("USER01");
        return bean;
    }
}
