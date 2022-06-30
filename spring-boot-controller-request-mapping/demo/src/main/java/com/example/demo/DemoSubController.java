package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// サブ画面に遷移するコントローラクラス
// @RequestMappingアノテーションで、このコントローラクラス共通で
// 利用するパスを指定する
// サブ画面への遷移するパス⇒http://(ホスト名):(ポート番号)/demo/sub/toSub
@Controller
@RequestMapping("/demo/sub")
public class DemoSubController {

    /**
     * サブ画面に遷移する
     * @return サブ画面
     */
    @PostMapping("/toSub")
    public String toSub(){
        return "/sub/sub";
    }

    /**
     * 初期表示画面に戻る
     * @return 初期表示画面
     */
    @PostMapping("/back/toIndex")
    public String backToIndex(){
        return "index";
    }
}
