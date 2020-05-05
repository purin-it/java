package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemoController {

    /**
     * 初期表示画面に遷移する
     * @return 初期表示画面へのパス
     */
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 次画面に遷移する
     * @return 次画面へのパス
     */
    @RequestMapping("/next")
    public String next() {
        return "next";
    }

    /**
     * 初期表示画面に戻る
     * @return 初期表示画面へのパス
     */
    @RequestMapping("/back")
    public String back() {
        return "index";
    }
}
