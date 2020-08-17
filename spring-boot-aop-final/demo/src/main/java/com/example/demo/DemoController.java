package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

    /**
     * Demoサービスクラスへのアクセス
     */
    @Autowired
    private DemoService demoService;

    /**
     * 初期表示画面に遷移する
     * @return 初期表示画面へのパス
     */
    @GetMapping("/")
    public String index(){
        return "index";
    }

    /**
     * ユーザーデータを取得し、final未使用_確認用画面に遷移する
     * @param model Modelオブジェクト
     * @return final未使用_確認用画面へのパス
     */
    @GetMapping("/index_nofinal")
    public String index_nofinal(Model model){
        String str = demoService.getUserData();
        model.addAttribute("userData", str);
        return "index_nofinal";
    }

    /**
     * ユーザーデータを取得し、final使用_確認用画面に遷移する
     * @param model Modelオブジェクト
     * @return final使用_確認用画面へのパス
     */
    @GetMapping("/index_final")
    public String index_final(Model model){
        String str = demoService.getUserDataByFinal();
        model.addAttribute("userDataByFinal", str);
        return "index_final";
    }
}
