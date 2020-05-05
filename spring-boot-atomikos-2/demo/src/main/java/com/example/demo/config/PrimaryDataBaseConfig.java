package com.example.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

//application.ymlに指定したPrimaryデータベースの設定を取得する
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.primary")
@Data
public class PrimaryDataBaseConfig {

    /** URL */
    private String url;

    /** ユーザー名 */
    private String username;

    /** パスワード */
    private String password;

    /** ドライバクラス名 */
    private String driverClassName;

}
