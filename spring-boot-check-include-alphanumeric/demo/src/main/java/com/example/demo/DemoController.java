package com.example.demo;

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
     * 初期表示画面に遷移する
     * @return 初期表示画面へのパス
     */
    @GetMapping("/")
    public String index(){
        return "index";
    }

    /**
     * エラーチェックを行い、エラーが無ければメイン画面に遷移し、
     * エラーがあれば初期表示画面のままとする
     * @param demoForm Formオブジェクト
     * @param result バインド結果
     * @return メイン画面または初期表示画面へのパス
     */
    @PostMapping("/main")
    public String main(@Validated DemoForm demoForm, BindingResult result){
        // formオブジェクトのチェック処理を行う
        if(result.hasErrors()){
            // エラーがある場合は、初期表示画面のままとする
            return "index";
        }
        // メイン画面に遷移する
        return "main";
    }

    /**
     * 初期表示画面に戻る
     * @param sessionStatus セッションステータス
     * @return ログイン画面
     */
    @PostMapping(value = "/back")
    public String back(SessionStatus sessionStatus){
        // セッションオブジェクトを破棄する
        sessionStatus.setComplete();
        return "index";
    }

}