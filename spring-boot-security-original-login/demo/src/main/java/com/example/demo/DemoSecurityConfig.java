package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;

@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) {
        //org.springframework.security.web.firewall.RequestRejectedException:
        //The request was rejected because the URL contained a potentially malicious String ";"
        //というエラーログがコンソールに出力されるため、下記を追加
        DefaultHttpFirewall firewall = new DefaultHttpFirewall();
        web.httpFirewall(firewall);
    }

    /**
     * SpringSecurityによる認証を設定
     * @param http HttpSecurityオブジェクト
     * @throws Exception 例外
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //初期表示画面を表示する際にログイン画面を表示する
        http.formLogin()
                //ログイン画面は常にアクセス可能とする
                .loginPage("/login").permitAll()
                //ログインに成功したら検索画面に遷移する
                .defaultSuccessUrl("/")
                .and()
                //ログイン画面のcssファイルとしても共通のdemo.cssを利用するため、
                //src/main/resources/static/cssフォルダ下は常にアクセス可能とする
                .authorizeRequests().antMatchers("/css/**").permitAll()
                .and()    //かつ
                //それ以外の画面は全て認証を有効にする
                .authorizeRequests().anyRequest().authenticated()
                .and()    //かつ
                //ログアウト時はログイン画面に遷移する
                .logout().logoutSuccessUrl("/login").permitAll();
    }

    /**
     * メモリ上にユーザー・パスワードを格納する処理
     * @param auth　認証マネージャー生成ツール
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //ユーザー名「user」、パスワード「pass」が入力されたらログイン可能とする
        //パスワードエンコーダーを利用しないようにするため、パスワードの先頭に{noop}を
        //指定している
        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}pass").roles("USER");
    }
}
