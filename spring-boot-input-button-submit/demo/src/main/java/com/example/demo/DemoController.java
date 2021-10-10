package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemoController {

    /* Spring Bootでログ出力するためのLogbackのクラスを生成 */
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

    /**
     * 初期表示画面に遷移する.
     * @return 初期表示画面
     */
    @RequestMapping("/")
    public String index(){
        return "index";
    }

    /**
     * method1メソッドを呼び出す.
     * @return 初期表示画面
     */
    @PostMapping("/method1")
    public String method1(){
        LOGGER.info("method1メソッドが呼ばれました。");
        return "index";
    }

    /**
     * method2メソッドを呼び出す.
     * @return 初期表示画面
     */
    @PostMapping("/method2")
    public String method2(){
        LOGGER.info("method2メソッドが呼ばれました。");
        return "index";
    }
}
