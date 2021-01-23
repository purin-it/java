package com.example.demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DemoInvocation{
    //ログ出力のためのクラス
    private static Log log = LogFactory.getLog(DemoInvocation.class);

    //エラー発生を判定するフラグ
    private static boolean isErrorFlg = true;

    /**
     * Aroundアノテーションにより、指定したメソッドの前後に処理を追加する
     * Aroundアノテーションの引数には、Pointcut式を指定
     *
     * @param jp 横断的な処理を挿入する場所
     * @return 指定したメソッドの戻り値
     */
    @Around("execution(* com.example.demo.*Controller.*(..))")
    public Object writeLog(ProceedingJoinPoint jp){
        //返却オブジェクトを定義
        Object returnObj = null;
        //指定したクラスの指定したメソッド名・戻り値を取得
        String signature = jp.getSignature().toString();

        //開始ログを出力
        log.debug("start writeLog : " + signature);
        log.debug("isErrorFlg : " + isErrorFlg);

        //エラーフラグが設定されている場合は、
        //DemoControllerクラスのtoErrorメソッドを呼び出す
        if(isErrorFlg){
            //次回呼出時はエラーにしないようfalseに設定
            isErrorFlg = false;
            returnObj = "redirect:/toError";
            return returnObj;
        }

        try {
            //指定したクラスの指定したメソッドを実行
            returnObj = jp.proceed();
        }catch(Throwable t){
            log.error("error writeLog : ", t);
        }

        //終了ログを出力
        log.debug("end writeLog : " + signature);

        //指定したクラスの指定したメソッドの戻り値を返却
        //このように実行しないと、Controllerクラスの場合、次画面遷移が行えない
        return returnObj;
    }

}