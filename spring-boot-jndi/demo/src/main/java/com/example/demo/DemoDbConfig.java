package com.example.demo;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * データベース設定クラス
 */
@Configuration
public class DemoDbConfig {

    /**
     * データソース名(application.propertiesから取得)
     */
    @Value("${spring.datasource.name}")
    private String jndiName;

    /**
     * データベースURL(application.propertiesから取得)
     */
    @Value("${spring.datasource.url}")
    private String url;

    /**
     * データベースユーザー名(application.propertiesから取得)
     */
    @Value("${spring.datasource.username}")
    private String username;

    /**
     * データベースパスワード(application.propertiesから取得)
     */
    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public TomcatServletWebServerFactory tomcatFactory(){
        //組み込みTomcatにデータベースJNDI接続情報を登録
        return new TomcatServletWebServerFactory(){
            @Override
            protected TomcatWebServer getTomcatWebServer(Tomcat tomcat){
                tomcat.enableNaming();
                return super.getTomcatWebServer(tomcat);
            }
            @Override
            protected void postProcessContext(Context context) {
                ContextResource resource = new ContextResource();
                resource.setProperty("factory", "org.apache.tomcat.jdbc.pool.DataSourceFactory");
                resource.setName(jndiName);
                resource.setType(DataSource.class.getName());
                resource.setProperty("driverClassName", "oracle.jdbc.driver.OracleDriver");
                resource.setProperty("url", url);
                resource.setProperty("username", username);
                resource.setProperty("password", password);
                context.getNamingResources().addResource(resource);
            }
        };
    }
}
