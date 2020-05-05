package com.example.demo.config;

import oracle.jdbc.xa.client.OracleXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import javax.sql.DataSource;
import java.sql.SQLException;

//SecondaryデータベースとMapperクラスの紐づけを行う
//@MapperScanアノテーションにて、アノテーションのbasePackages下に指定したMapperオブジェクトと、
//sqlSessionTemplateRefで指定した(接続先データベース情報を含む)SqlセッションTemplateオブジェクトを関連付ける
@Configuration
@MapperScan(basePackages = "com.example.demo.mapper.secondary", sqlSessionTemplateRef = "secondarySqlSessionTemplate")
public class SecondaryMyBatisConfig {

    /**
     * Secondaryデータベースのデータソースオブジェクトを生成する
     * @param dbConfig Secondaryデータベースの設定
     * @return データソースオブジェクト
     * @throws SQLException SQL例外
     */
    @Bean(name = "secondaryDataSource")
    public DataSource createPrimaryDataSource(SecondaryDataBaseConfig dbConfig) throws SQLException {
        //OracleXAデータソースオブジェクトを作成
        OracleXADataSource xaDataSource = new OracleXADataSource();
        //URL・ユーザー名・パスワードを引数の定義ファイルから取得し設定
        xaDataSource.setURL(dbConfig.getUrl());
        xaDataSource.setUser(dbConfig.getUsername());
        xaDataSource.setPassword(dbConfig.getPassword());
        //AtomikosデータソースBeanオブジェクトを生成
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        //一意なリソース名・OracleXAデータソースオブジェクトを設定し返却
        atomikosDataSourceBean.setUniqueResourceName("secondary");
        atomikosDataSourceBean.setXaDataSource(xaDataSource);
        return atomikosDataSourceBean;
    }

    /**
     * SecondaryデータベースのSqlセッションファクトリBeanオブジェクトを生成する
     * @param dataSource データソースオブジェクト
     * @return SqlセッションファクトリBeanオブジェクト
     * @throws Exception　例外
     */
    @Bean(name = "secondarySqlSessionFactory")
    public SqlSessionFactory createSqlSessionFactory(@Qualifier("secondaryDataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //Mapperクラス内で参照しているXMLファイルのパスを指定
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(
                "classpath:/com/example/demo/mapper/secondary/UserDataMapperSecondary.xml"));
        return bean.getObject();
    }

    /**
     * SecondaryデータベースのSqlセッションTemplateオブジェクトを生成する
     * @param sqlSessionFactory SqlセッションTemplateオブジェクト
     * @return SqlセッションファクトリBeanオブジェクト
     */
    @Bean(name = "secondarySqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(
            @Qualifier("secondarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
