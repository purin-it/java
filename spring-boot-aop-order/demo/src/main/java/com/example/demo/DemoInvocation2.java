package com.example.demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(20)
//@Order(5)
public class DemoInvocation2 {

    // ログ出力のためのクラス
    private static Log log = LogFactory.getLog(DemoInvocation2.class);

    /**
     * Beforeアノテーションにより、指定したメソッドの前に処理を追加する
     * Beforeアノテーションの引数には、Pointcut式 execution(戻り値 パッケージ.クラス.メソッド(引数))
     * を指定し、ここではControllerクラスの全メソッドの実行前にログ出力するようにしている
     *
     * @param jp 横断的な処理を挿入する場所
     */
    @Before("execution(* com.example.demo.*Controller.*(..))")
    public void beforeAnnotationLog2(JoinPoint jp) {
        // ログを出力
        String signature = jp.getSignature().toString();
        log.debug("called beforeAnnotationLog2 : " + signature);
    }

    /**
     * Afterアノテーションにより、指定したメソッドの後に処理を追加する
     * Afterアノテーションの引数には、Pointcut式を指定
     *
     * @param jp 横断的な処理を挿入する場所
     */
    @After("execution(* com.example.demo.*Controller.*(..))")
    public void afterAnnotationLog2(JoinPoint jp) {
        // ログを出力
        String signature = jp.getSignature().toString();
        log.debug("called afterAnnotationLog2 : " + signature);
    }

    /**
     * AfterReturningアノテーションにより、指定したメソッドが正常終了した場合に、
     * 指定したメソッドの後に処理を追加する
     * AfterReturningアノテーションの引数には、Pointcut式を指定
     *
     * @param jp 横断的な処理を挿入する場所
     * @param returnValue 指定したメソッドの戻り値
     */
    @AfterReturning(value = "execution(* com.example.demo.*Controller.*(..))"
            , returning = "returnValue")
    public void afterReturningAnnotationLog2(JoinPoint jp, Object returnValue) {
        // ログを出力
        String signature = jp.getSignature().toString();
        log.debug("called afterReturningAnnotationLog2 : " + signature
                + ", returning : " + returnValue);
    }

    /**
     * Aroundアノテーションにより、指定したメソッドの前後に処理を追加する
     * Aroundアノテーションの引数には、Pointcut式を指定
     *
     * @param jp 横断的な処理を挿入する場所
     * @return 指定したメソッドの戻り値
     */
    @Around("execution(* com.example.demo.*Controller.*(..))")
    public Object aroundAnnotationLog2(ProceedingJoinPoint jp) {
        //返却オブジェクトを定義
        Object returnObj = null;
        //指定したクラスの指定したメソッド名・戻り値の型を取得
        String signature = jp.getSignature().toString();
        //開始ログを出力
        log.debug("called aroundAnnotationLog2 start : " + signature);
        try {
            //指定したクラスの指定したメソッドを実行
            returnObj = jp.proceed();
        } catch (Throwable t) {
            log.error("error aroundAnnotationLog2 : ", t);
        }
        //終了ログを出力
        log.debug("called aroundAnnotationLog2 end : " + signature);
        //指定したクラスの指定したメソッドの戻り値を返却
        //このように実行しないと、Controllerクラスの場合、次画面遷移が行えない
        return returnObj;
    }

}