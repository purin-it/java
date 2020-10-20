package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DemoController {

    /**
     * 初期表示画面を開く
     * @return 初期表示画面へのパス
     */
    @GetMapping("/")
    public String index(){
        return "index";
    }

    /**
     * 新規画面(GETリクエスト用)を開く
     * @return 新規画面(GETリクエスト用)
     */
    @GetMapping("/openWinByGet")
    public String openWinByGet(){
        return "new_window1";
    }

    /**
     * 新規画面(POSTリクエスト用)を開く
     * @return 新規画面(POSTリクエスト用)
     */
    @PostMapping("/openWinByPost")
    public String openWinByPost(){
        return "new_window2";
    }

}
