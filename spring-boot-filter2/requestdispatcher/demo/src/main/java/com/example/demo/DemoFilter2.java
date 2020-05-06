package com.example.demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import java.io.IOException;

public class DemoFilter2 implements Filter {
    //ログ出力のためのクラス
    private static Log log = LogFactory.getLog(DemoFilter2.class);

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
        log.debug("DemoFilter2 started.");
        try{
            //ここでArithmeticExceptionを強制的に発生させる
            int i = 1 / 0;
        }catch(Exception e){
            //エラーログを出力
            log.error(e.toString());
            StackTraceElement[] errors = e.getStackTrace();
            for(StackTraceElement element : errors){
                log.error(element);
            }
            //RequestMappingが「/error_filter」であるコントローラのメソッド
            //(DemoController.java、errorFilter)を呼び出す
            RequestDispatcher rd = request.getRequestDispatcher(
                    "/error_filter?exception=" + e.toString());
            rd.forward(request, response);
            return;
        }
        //一連の処理(本プログラムではコントローラクラスのメソッド)を実行
        chain.doFilter(request, response);
        log.debug("DemoFilter2 ended.");
    }
}