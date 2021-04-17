package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class DemoConfigBean {

    /**
     * 接続タイムアウト
     */
    @Value("${timeout.connect}")
    private String timeoutConnect;

    /**
     * 読み取りタイムアウト
     */
    @Value("${timeout.read}")
    private String timeoutRead;

    /**
     * RestTemplateオブジェクトを作成する
     * @return RestTemplateオブジェクト
     */
    @Bean
    public RestTemplate getRestTemplate(){
        //　接続タイムアウト、読み取りタイムアウトを設定しRestTemplateオブジェクトを返却
        RestTemplate template = new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(Integer.parseInt(timeoutConnect)))
                .setReadTimeout(Duration.ofSeconds(Integer.parseInt(timeoutRead)))
                .build();
        return template;
    }
}
