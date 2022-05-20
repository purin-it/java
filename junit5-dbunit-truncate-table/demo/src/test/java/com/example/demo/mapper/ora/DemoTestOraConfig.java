package com.example.demo.mapper.ora;

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

// MyBatisのテストを実行する際のDB接続定義
// @MybatisTestアノテーションでテストを実行する際、
// Spring Bootアプリケーションが起動するようにする。
// その際、com.example.demo.mapper.oraフォルダ下のインスタンス
// (UserDataMapper)のみがDIで使用できるようになる。
@SpringBootApplication
@PropertySource(value = {"classpath:test.properties"})
public class DemoTestOraConfig {

    /**
     * Oracleのデータソースプロパティ(UT)を生成する.
     * @return Oracleのデータソースプロパティ(UT)
     */
    @Bean(name = {"datasourceOraProperties"})
    @Primary
    @ConfigurationProperties(prefix = "spring.datasourceut-ora")
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
     * OracleのDB接続設定(UT)を生成する.
     * @return OracleのDB接続設定(UT)
     */
    @Bean(name = {"dbUnitDatabaseConfigOra"})
    public DatabaseConfigBean dbUnitDatabaseConfigOra() {
        DatabaseConfigBean bean = new DatabaseConfigBean();
        bean.setAllowEmptyFields(true);
        bean.setDatatypeFactory(new Oracle10DataTypeFactory());
        return bean;
    }

    /**
     * Oracleのデータソースコネクションファクトリ(UT)を生成する.
     * @param dbUnitDatabaseConfigOra OracleのDB接続設定(UT)
     * @param dataSourceOra           Oracleのデータソース(UT)
     * @return Oracleのデータソースコネクションファクトリ(UT)
     */
    @Bean(name = {"dbUnitDatabaseConnectionOra"})
    public DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnectionOra(
            @Qualifier("dbUnitDatabaseConfigOra") DatabaseConfigBean dbUnitDatabaseConfigOra,
            @Qualifier("dataSourceOra") DataSource dataSourceOra) {
        DatabaseDataSourceConnectionFactoryBean bean = new DatabaseDataSourceConnectionFactoryBean(dataSourceOra);
        bean.setDatabaseConfig(dbUnitDatabaseConfigOra);
        bean.setSchema("USER01");
        return bean;
    }
}
