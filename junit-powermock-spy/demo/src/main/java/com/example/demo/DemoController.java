package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemoController {

    @RequestMapping("/")
    public String index(Model model){
        String realString1 = DemoUtil.getRealString1();
        String realString2 = DemoUtil.getRealString2();
        DemoUtil.testVoid();

        model.addAttribute("realString1", realString1);
        model.addAttribute("realString2", realString2);
        return "index";
    }
}
