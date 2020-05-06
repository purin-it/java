package com.example.demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import java.io.IOException;

public class DemoFilter1 implements Filter {
    //ログ出力のためのクラス
    private static Log log = LogFactory.getLog(DemoFilter1.class);

    /**
     * 処理(本プログラムではコントローラクラスのメソッド)の前後にログ出力を行うフィルタ定義
     * @param request サーブレットリクエスト
     * @param response サーブレットレスポンス
     * @param chain フィルタチェイン
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response
            , FilterChain chain) throws IOException, ServletException {
        log.debug("DemoFilter1 started.");
        //一連の処理(本プログラムではコントローラクラスのメソッド)を実行
        chain.doFilter(request, response);
        log.debug("DemoFilter1 ended.");
    }
}
