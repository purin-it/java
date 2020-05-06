package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class DemoComponent {

    public String getMessageFromComponent(){
        return "このメッセージはDemoComponentから取得しました";
    }

}