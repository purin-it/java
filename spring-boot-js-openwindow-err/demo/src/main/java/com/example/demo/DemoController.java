package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 新規画面(POSTリクエスト用)を開く際にエラー
     * @return 新規画面(POSTリクエスト用_エラー)
     */
    @PostMapping("/openWinByPostErr")
    public String openWinByPostErr(Model model){
        List<String> errList = new ArrayList<>();
        errList.add("新規ウィンドウオープン時のエラーメッセージ１");
        errList.add("新規ウィンドウオープン時のエラーメッセージ２");
        errList.add("新規ウィンドウオープン時のエラーメッセージ３");
        model.addAttribute("errList", errList);
        return "new_window3";
    }
}
