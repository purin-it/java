package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemoController {

    /**
     * DemoComponentクラス
     * (インスタンス生成をコンストラクタで行うため、アノテーションは付与していない)
     */
    DemoComponent demoComponent;

    /**
     * コンストラクタ
     */
    public DemoController(){
        demoComponent = new DemoComponent();
    }

    /**
     * 初期表示画面に遷移する
     * @param model Modelオブジェクト
     * @return 初期表示画面へのパス
     */
    @RequestMapping("/")
    public String index(Model model){
        String nowDateTime = demoComponent.getNowDateTime();
        String nowDateTimeFinal = demoComponent.getNowDateTimeFinal();

        model.addAttribute("nowDateTime", nowDateTime);
        model.addAttribute("nowDateTimeFinal", nowDateTimeFinal);
        return "index";
    }
}
