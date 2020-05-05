package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DemoGetPortNum {

    /**　ポート番号 */
    @Value("${server.port}")
    private String portNum;

    /**
     * ポート番号を取得する
     * @return ポート番号
     */
    public String getPortNum(){
        return portNum;
    }

}
