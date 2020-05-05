package com.example.demo.base;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import java.util.Random;

@Configuration
public class DemoConfigBean {

    /**
     * リクエストトークンのInterceptorをBean定義に追加
     * @return リクエストトークンのInterceptor
     */
    @Bean
    public HandlerInterceptor requestTokenInterceptor() {
        return new RequestTokenInterceptor();
    }

    /**
     * 乱数生成クラスのインスタンスをBean定義に追加
     * @return 乱数生成クラスのインスタンス
     */
    @Bean
    public Random makeRandom() {
        return new Random();
    }
}
