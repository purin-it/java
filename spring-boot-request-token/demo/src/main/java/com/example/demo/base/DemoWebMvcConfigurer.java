package com.example.demo.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class DemoWebMvcConfigurer implements WebMvcConfigurer {

    /**
     * リクエストトークンのInterceptor
     */
    @Autowired
    private HandlerInterceptor requestTokenInterceptor;

    /**
     * 作成したInterceptorをSpringに認識させる
     *
     * @param registry Interceptorリポジトリ
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestTokenInterceptor);
    }
}
