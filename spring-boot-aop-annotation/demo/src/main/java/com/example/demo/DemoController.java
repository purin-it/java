package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@DemoAnnotation
public class DemoController {

    /**
     * 初期表示画面に遷移する
     * @return 初期表示画面へのパス
     */
    @GetMapping("/")
    public String index(){
        return "index";
    }

    /**
     * メソッドに独自アノテーションが有る場合の処理
     * @return 初期表示画面へのパス
     */
    @GetMapping("/has_method_annotation")
    @DemoAnnotation
    public String has_method_annotation(){
        return "index";
    }

    /**
     * メソッドに独自アノテーションが無い場合の処理
     * @return 初期表示画面へのパス
     */
    @GetMapping("/has_no_method_annotation")
    public String has_no_method_annotation(){
        return "index";
    }
}
