package com.example.demo;

import java.time.LocalDateTime;

public class DemoComponent {

    /**
     * 現在日時を取得し返却する
     * @return 現在日時
     */
    public String getNowDateTime(){
        LocalDateTime nowDateTime = LocalDateTime.now();
        return nowDateTime.toString();
    }

    /**
     * finalメソッドで現在日時を取得し返却する
     * @return 現在日時
     */
    public final String getNowDateTimeFinal(){
        LocalDateTime nowDateTime = LocalDateTime.now();
        return nowDateTime.toString();
    }
}
