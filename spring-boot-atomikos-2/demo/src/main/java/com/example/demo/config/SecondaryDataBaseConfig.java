package com.example.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

//application.ymlに指定したSecondaryデータベースの設定を取得する
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.secondary")
@Data
public class SecondaryDataBaseConfig {

    /** URL */
    private String url;

    /** ユーザー名 */
    private String username;

    /** パスワード */
    private String password;

    /** ドライバクラス名 */
    private String driverClassName;

}
