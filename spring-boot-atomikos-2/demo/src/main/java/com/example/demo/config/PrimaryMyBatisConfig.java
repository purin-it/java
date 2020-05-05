package com.example.demo.config;

import javax.sql.DataSource;
import oracle.jdbc.xa.client.OracleXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import java.sql.SQLException;

//PrimaryデータベースとMapperクラスの紐づけを行う
//@MapperScanアノテーションにて、アノテーションのbasePackages下に指定したMapperオブジェクトと、
//sqlSessionTemplateRefで指定した(接続先データベース情報を含む)SqlセッションTemplateオブジェクトを関連付ける
@Configuration
@MapperScan(basePackages = "com.example.demo.mapper.primary", sqlSessionTemplateRef = "primarySqlSessionTemplate")
public class PrimaryMyBatisConfig {

    /**
     * Primaryデータベースのデータソースオブジェクトを生成する
     * @param dbConfig Primaryデータベースの設定
     * @return データソースオブジェクト
     * @throws SQLException SQL例外
     */
    @Primary
    @Bean(name = "primaryDataSource")
    public DataSource createPrimaryDataSource(PrimaryDataBaseConfig dbConfig) throws SQLException {
        //OracleXAデータソースオブジェクトを作成
        OracleXADataSource xaDataSource = new OracleXADataSource();
        //URL・ユーザー名・パスワードを引数の定義ファイルから取得し設定
        xaDataSource.setURL(dbConfig.getUrl());
        xaDataSource.setUser(dbConfig.getUsername());
        xaDataSource.setPassword(dbConfig.getPassword());
        //AtomikosデータソースBeanオブジェクトを生成
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        //一意なリソース名・OracleXAデータソースオブジェクトを設定し返却
        atomikosDataSourceBean.setUniqueResourceName("primary");
        atomikosDataSourceBean.setXaDataSource(xaDataSource);
        return atomikosDataSourceBean;
    }

    /**
     * PrimaryデータベースのSqlセッションファクトリBeanオブジェクトを生成する
     * @param dataSource データソースオブジェクト
     * @return SqlセッションファクトリBeanオブジェクト
     * @throws Exception　例外
     */
    @Primary
    @Bean(name = "primarySqlSessionFactory")
    public SqlSessionFactory createSqlSessionFactory(@Qualifier("primaryDataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //Mapperクラス内で参照しているXMLファイルのパスを指定
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(
                "classpath:/com/example/demo/mapper/primary/UserDataMapperPrimary.xml"));
        return bean.getObject();
    }

    /**
     * PrimaryデータベースのSqlセッションTemplateオブジェクトを生成する
     * @param sqlSessionFactory SqlセッションTemplateオブジェクト
     * @return SqlセッションファクトリBeanオブジェクト
     */
    @Primary
    @Bean(name = "primarySqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(
            @Qualifier("primarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
