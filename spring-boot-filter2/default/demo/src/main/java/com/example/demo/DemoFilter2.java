package com.example.demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import java.io.IOException;

public class DemoFilter2 implements Filter {
    //ログ出力のためのクラス
    private static Log log = LogFactory.getLog(DemoFilter2.class);

    /**
     * 処理(本プログラムではコントローラクラスのメソッド)の前後にログ出力
     * を行うフィルタ定義
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
            //例外をスローすると、resources/templates/error.htmlに遷移
            throw e;
        }
        //一連の処理(本プログラムではコントローラクラスのメソッド)を実行
        chain.doFilter(request, response);
        log.debug("DemoFilter2 ended.");
    }
}