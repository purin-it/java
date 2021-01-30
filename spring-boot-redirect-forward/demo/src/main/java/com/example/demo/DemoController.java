package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DemoController {

    /**
     * 初期表示画面に遷移する
     * @return 初期表示画面
     */
    @GetMapping("/")
    public String index(){
        return "index";
    }

    /**
     * メイン画面にGETメソッドで遷移する
     * @param model Modelオブジェクト
     * @return メイン画面
     */
    @GetMapping("/toMainGet")
    public String toMainGet(Model model){
        model.addAttribute("passedMethod", "toMainGetメソッド");
        return "main";
    }

    /**
     * メイン画面にPOSTメソッドで遷移する
     * @param model Modelオブジェクト
     * @return メイン画面
     */
    @PostMapping("/toMainPost")
    public String toMainPost(Model model){
        model.addAttribute("passedMethod", "toMainPostメソッド");
        return "main";
    }

    /**
     * リダイレクトでメイン画面に遷移する
     * @param redirectAttributes リダイレクト属性値
     * @return メイン画面
     */
    @PostMapping("/toRedirect")
    public String toRedirect(RedirectAttributes redirectAttributes){
        // メイン画面に渡す値を設定する
        // リダイレクト遷移する場合はFlash Scopeを利用する
        redirectAttributes.addFlashAttribute("pushedBtn", "リダイレクト遷移ボタン");

        // リダイレクトでメイン画面に遷移する
        // このとき、リダイレクト遷移先はGETメソッドとする
        return "redirect:/toMainGet";
    }

    /**
     * フォワードでメイン画面に遷移する
     * @param model Modelオブジェクト
     * @return メイン画面
     */
    @PostMapping("/toForward")
    public String toForward(Model model){
        // メイン画面に渡す値を設定する
        // フォワード遷移する場合はModelオブジェクトに値を設定する
        model.addAttribute("pushedBtn", "フォワード遷移ボタン");

        // フォワードでメイン画面に遷移する
        // このとき、リダイレクト遷移先はPOSTメソッドのままとする
        return "forward:/toMainPost";
    }

    /**
     * 初期表示画面に戻る
     * @return 初期表示画面
     */
    @PostMapping("/toIndex")
    public String toIndex(){
        return "index";
    }
}
