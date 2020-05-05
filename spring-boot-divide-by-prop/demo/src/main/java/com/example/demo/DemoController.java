package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemoController {

    /**
     * demo.testの値取得処理
     */
    @Autowired
    private DemoTest demoTest;

    @RequestMapping("/")
    public String index(Model model){
        //demo.testの値を取得し、modelオブジェクトに設定
        String testValue = demoTest.gestTestValue();
        model.addAttribute("testValue", testValue);
        return "index";
    }
}
