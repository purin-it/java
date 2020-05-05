package com.example.demo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix="spring.datasource")
@Component
@Data
public class DemoGetDataSource {

    /** URL */
    private String url;

    /** ユーザー名 */
    private String username;

    /** パスワード */
    private String password;

    /** ドライバクラス名 */
    private String driverClassName;

    /**
     * データベース接続情報を取得する
     * @return データベース接続情報
     */
    public String getDataSource(){
        return "url=" + url
                + ", username=" + username
                + ", password=" + password
                + ", driverClassName=" + driverClassName;
    }
}
