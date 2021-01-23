package com.example.demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

    //ログ出力のためのクラス
    private static Log log = LogFactory.getLog(DemoController.class);

    /**
     * 初期表示画面に遷移する
     * @return 初期表示画面
     */
    @GetMapping("/")
    public String index(){
        log.debug("com.example.demo.DemoController.index() called.");
        return "index";
    }

    /**
     * エラー画面に遷移する
     * @return エラー画面
     */
    @GetMapping("/toError")
    public String toError(){
        log.debug("com.example.demo.DemoController.toError() called.");
        return "error";
    }
}
