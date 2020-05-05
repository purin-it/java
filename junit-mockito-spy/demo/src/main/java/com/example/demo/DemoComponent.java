package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class DemoComponent {

    public String getRealString1(){
        return "realString1";
    }

    public String getRealString2(){
        return "realString2";
    }

    public void testVoid(){
        System.out.println("testVoid");
    }
}
