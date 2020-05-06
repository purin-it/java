package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class DemoComponent {
    public String getString(){
        return "from DemoComponent";
    }
}