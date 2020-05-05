package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemoController {

    @Autowired
    private DemoComponent demoComponent;

    @RequestMapping("/")
    public String index(Model model){
        String realString1 = demoComponent.getRealString1();
        String realString2 = demoComponent.getRealString2();
        demoComponent.testVoid();

        model.addAttribute("realString1", realString1);
        model.addAttribute("realString2", realString2);
        return "index";
    }
}
