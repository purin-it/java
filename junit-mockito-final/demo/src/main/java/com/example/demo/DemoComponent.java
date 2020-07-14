package com.example.demo;

import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
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
