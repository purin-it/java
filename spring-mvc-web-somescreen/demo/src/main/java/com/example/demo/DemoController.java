package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * コントローラクラス
 * 「@SessionAttributes(types = DemoForm.class)」により、
 * 生成したFormオブジェクトをセッションとしてもたせている
 */
@Controller
@SessionAttributes(types = DemoForm.class)
public class DemoController {

    /**
     * Formオブジェクトを初期化して返却する
     * @return Formオブジェクト
     */
    @ModelAttribute("demoForm")
    public DemoForm createDemoForm(){
        DemoForm demoForm = new DemoForm();
        //名前・性別の初期値を設定する
        demoForm.setName("テスト　名前");
        demoForm.setSex("1");
        return demoForm;
    }

    /**
     * 入力画面に遷移する
     * @param demoForm Formオブジェクト
     * @return 入力画面へのパス
     */
    @RequestMapping("/")
    public String index(DemoForm demoForm){
        return "input";
    }

    /**
     * 確認画面に遷移する
     * @param demoForm Formオブジェクト
     * @return 確認画面へのパス
     */
    @RequestMapping("/confirm")
    public String confirm(DemoForm demoForm){
        return "confirm";
    }

    /**
     * 完了画面に遷移する
     * @return 完了画面へのパス
     */
    @RequestMapping(value = "/send")
    public String send(){
        return "complete";
    }

}
