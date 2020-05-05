package com.example.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DemoInvocation {

    //ログ出力のためのクラス
    private Logger logger = LogManager.getLogger(DemoInvocation.class);

    /**
     * Beforeアノテーションにより、指定したメソッドの前に処理を追加する
     * Beforeアノテーションの引数には、Pointcut式 execution(戻り値 パッケージ.クラス.メソッド(引数))を
     * 指定し、ここではControllerクラスの全メソッドの実行前にログ出力するようにしている
     *
     * @param jp 横断的な処理を挿入する場所
     */
    @Before("execution(public String com.example.demo.*Controller.*(..))")
    public void startLog(JoinPoint jp){
        //開始ログを出力
        String signature = jp.getSignature().toString();
        logger.info("開始ログ : " + signature);
    }

    /**
     * Afterアノテーションにより、指定したメソッドの前に処理を追加する
     * Afterアノテーションの引数には、Pointcut式を指定
     *
     * @param jp 横断的な処理を挿入する場所
     */
    @After("execution(public String com.example.demo.*Controller.*(..))")
    public void endLog(JoinPoint jp){
        //終了ログを出力
        String signature = jp.getSignature().toString();
        logger.info("終了ログ : " + signature);
    }

}
