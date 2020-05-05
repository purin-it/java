package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DemoController {

    @RequestMapping("/")
    public String index(Model model){
        List<String> strNumList = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            strNumList.add("000000" + (i + 1));
        }
        model.addAttribute("strNumList", strNumList);
        return "index";
    }
}
