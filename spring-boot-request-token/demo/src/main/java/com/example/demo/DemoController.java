package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DemoController {

    /**
     * 初期表示画面に遷移する
     * @return 初期表示画面へのパス
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 次画面に遷移する
     * @return 次画面へのパス
     */
    @PostMapping("/next")
    public String next() {
        return "next";
    }

    /**
     * 初期表示画面に戻る
     * @return 初期表示画面へのパス
     */
    @PostMapping("/back")
    public String back() {
        return "index";
    }
}
