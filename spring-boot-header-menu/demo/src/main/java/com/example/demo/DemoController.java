package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/header1")
    public String header1(Model model){
        model.addAttribute("select_header", "header1");
        model.addAttribute("select_header_name", "ヘッダー１");
        return "index";
    }

    @GetMapping("/header2")
    public String header2(Model model){
        model.addAttribute("select_header", "header2");
        model.addAttribute("select_header_name", "少し長いヘッダー２");
        return "index";
    }

    @GetMapping("/header3")
    public String header3(Model model){
        model.addAttribute("select_header", "header3");
        model.addAttribute("select_header_name", "ヘッダー３");
        return "index";
    }

    @GetMapping("/header4")
    public String header4(Model model){
        model.addAttribute("select_header", "header4");
        model.addAttribute("select_header_name", "もう少し長いヘッダー４");
        return "index";
    }

    @GetMapping("/header5")
    public String header5(Model model){
        model.addAttribute("select_header", "header5");
        model.addAttribute("select_header_name", "ヘッダー５");
        return "index";
    }
}
