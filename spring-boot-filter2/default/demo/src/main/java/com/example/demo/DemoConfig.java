package com.example.demo;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * アプリケーションの設定を行うクラス
 * 「@Configuration」をクラスに付与し、この中に「@Bean」を付与したメソッドを記述すると、
 * アプリケーション起動時に、「@Bean」を付与したメソッドのコンポーネントが作成される
 */
@Configuration
public class DemoConfig {

    /**
     * フィルタ1のオブジェクトをコンポーネントに追加
     * @return フィルタ1を登録したBean
     */
    @Bean
    public FilterRegistrationBean demoFilter1(){
        //フィルタ1のオブジェクトを1番目に実行するフィルタとして追加
        FilterRegistrationBean bean = new FilterRegistrationBean(new DemoFilter1());
        //コントローラ・静的コンテンツ全てのリクエストに対してフィルタ1を有効化
        bean.addUrlPatterns("/*");
        //フィルタ1の実行順序を1に設定
        bean.setOrder(1);
        return bean;
    }

    /**
     * フィルタ2のオブジェクトをコンポーネントに追加
     * @return フィルタ2を登録したBean
     */
    @Bean
    public FilterRegistrationBean demoFilter2(){
        //フィルタ2のオブジェクトを2番目に実行するフィルタとして追加
        FilterRegistrationBean bean = new FilterRegistrationBean(new DemoFilter2());
        //コントローラ・静的コンテンツ全てのリクエストに対してフィルタ2を有効化
        bean.addUrlPatterns("/*");
        //フィルタ2の実行順序を2に設定
        bean.setOrder(2);
        return bean;
    }
}
