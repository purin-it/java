package com.example.demo;

import org.springframework.stereotype.Controller;

@Controller
public class DemoSubController {
    public String getString(){
        return "from DemoSubController";
    }
}