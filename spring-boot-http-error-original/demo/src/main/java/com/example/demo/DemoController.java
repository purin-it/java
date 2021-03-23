package com.example.demo;
 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemoController {
 
    /**
     * 初期表示画面を表示する
     * @return 初期表示画面
     */
    @GetMapping("/")
    public String index(){
        return "index";
    }
 
    /**
     * HTTP 500エラー(404以外)を発生させる
     * @return 存在しない画面
     */
    @PostMapping("/submit_error_other")
    public String submitErrorOther(){
        // 存在しない画面に遷移しようとするため、
        // HTTP 500エラーが発生する
        return "no_page";
    }

    /**
     * HTTP 404エラー発生後のエラー画面に遷移させる
     * @return HTTP 404エラー発生後のエラー画面
     */
    @RequestMapping("/notFoundNew")
    public String notFoundNew(){
        return "404new";
    }
}
