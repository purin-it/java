package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

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
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(DemoForm demoForm){
        return "input";
    }

    /**
     * 確認画面に遷移する
     * @param demoForm Formオブジェクト
     * @return 確認画面へのパス
     */
    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public String confirm(DemoForm demoForm){
        return "confirm";
    }

    /**
     * 完了画面へのリダイレクトパスに遷移する
     * @return 完了画面へのリダイレクトパス
     */
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public String send(){
        return "redirect:/complete";
    }

    /**
     * 完了画面に遷移する
     * @param sessionStatus セッションステータス
     * @return 完了画面
     */
    @RequestMapping(value = "/complete", method = RequestMethod.GET)
    public String complete(SessionStatus sessionStatus){
        //セッションオブジェクトを破棄
        sessionStatus.setComplete();
        return "complete";
    }
}