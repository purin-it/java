package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * staticメソッドからDIによるオブジェクトのメソッドを呼び出すサンプル
 */
@Component
public class DemoUtil {

    /** staticメソッドから呼び出せるよう、DIするオブジェクトをstatic変数で宣言 */
    private static DemoComponent demoComponent;

    /**
     * Spring Boot起動時に、DIするオブジェクトを生成し、先ほど宣言したstatic変数に設定
     * @param demoComponent DemoComponentインスタンス
     */
    @Autowired
    public void setDemoComponent(DemoComponent demoComponent){
        DemoUtil.demoComponent = demoComponent;
    }

    /**
     * DemoComponentオブジェクトからメッセージを取得するメソッドを呼び出す
     * @return 取得したメッセージ
     */
    public static String getMessage(){
        return demoComponent.getMessageFromComponent();
    }

}