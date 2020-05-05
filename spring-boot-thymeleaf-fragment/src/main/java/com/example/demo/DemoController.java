package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemoController {

    /**
     * 初期表示画面に遷移する
     * @return 初期表示画面へのパス
     */
    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("isBodyTag"
                , "これはBodyTagに設定したメッセージです。");
        return "index";
    }
}
