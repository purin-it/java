package com.example.demo;

import com.example.demo.order.All;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
        return demoForm;
    }

    /**
     * ログイン画面に遷移する
     * @return ログイン画面へのパス
     */
    @GetMapping("/")
    public String index(){
        return "login";
    }

    /**
     * エラーチェックを行い、エラーが無ければ確認画面に遷移し、
     * エラーがあれば入力画面のままとする
     * @param demoForm Formオブジェクト
     * @param result バインド結果
     * @return 確認画面または入力画面へのパス
     */
    @PostMapping("/login")
    public String login(@Validated(All.class) DemoForm demoForm, BindingResult result){
        // formオブジェクトのチェック処理を行う
        if(result.hasErrors()){
            // エラーがある場合は、ログイン画面のままとする
            return "login";
        }
        // メイン画面に遷移する
        return "main";
    }

    /**
     * ログイン画面に戻る
     * @param sessionStatus セッションステータス
     * @return ログイン画面
     */
    @PostMapping(value = "/back")
    public String back(SessionStatus sessionStatus){
        // セッションオブジェクトを破棄する
        sessionStatus.setComplete();
        return "login";
    }

}