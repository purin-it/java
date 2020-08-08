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
     * 入力画面に遷移する
     * @return 入力画面へのパス
     */
    @GetMapping("/")
    public String index(){
        return "input";
    }

    /**
     * エラーチェックを行い、エラーが無ければ確認画面に遷移し、
     * エラーがあれば入力画面のままとする
     * @param demoForm Formオブジェクト
     * @param result バインド結果
     * @return 確認画面または入力画面へのパス
     */
    @PostMapping("/confirm")
    public String confirm(@Validated DemoForm demoForm, BindingResult result){
        //formオブジェクトのチェック処理を行う
        if(result.hasErrors()){
            //エラーがある場合は、入力画面のままとする
            return "input";
        }
        //アノテーション以外のチェック処理を行い、画面遷移する
        return checkOthers(demoForm, result, "confirm");
    }

    /**
     * エラーチェックを行い、エラーが無ければ完了画面へのリダイレクトパスに遷移し、
     * エラーがあれば入力画面に戻す
     * @param demoForm Formオブジェクト
     * @param result バインド結果
     * @return 完了画面へのリダイレクトパスまたは入力画面へのパス
     */
    @PostMapping(value = "/send", params = "next")
    public String send(@Validated DemoForm demoForm, BindingResult result){
        //formオブジェクトのチェック処理を行う
        if(result.hasErrors()){
            //エラーがある場合は、入力画面に戻す
            return "input";
        }
        //アノテーション以外のチェック処理を行い、画面遷移する
        return checkOthers(demoForm, result, "redirect:/complete");
    }

    /**
     * 完了画面に遷移する
     * @return 完了画面
     */
    @GetMapping("/complete")
    public String complete(SessionStatus sessionStatus){
        //セッションオブジェクトを破棄
        sessionStatus.setComplete();
        return "complete";
    }

    /**
     * 入力画面に戻る
     * @return 入力画面
     */
    @PostMapping(value = "/send", params = "back")
    public String back(){
        return "input";
    }

    /**
     * アノテーション以外のチェック処理を行い、画面遷移先を返却
     * @param demoForm Formオブジェクト
     * @param result バインド結果
     * @param normalPath 正常時の画面遷移先
     * @return 正常時の画面遷移先または入力画面へのパス
     */
    private String checkOthers(DemoForm demoForm, BindingResult result, String normalPath){
        //** アノテーション以外のチェック処理を行う
        //** エラーがある場合は、エラーメッセージ・(エラー時に赤反転するための)
        //** エラーフィールドの設定を行い、入力画面のままとする
        //生年月日のチェック処理
        int checkDate = DateCheckUtil.checkDate(demoForm.getBirthYear()
                , demoForm.getBirthMonth(), demoForm.getBirthDay());
        switch(checkDate){
            case 1:
                //生年月日_年が空文字の場合のエラー処理
                result.rejectValue("birthYear", "validation.date-empty"
                        , new String[]{"生年月日_年"}, "");
                return "input";
            case 2:
                //生年月日_月が空文字の場合のエラー処理
                result.rejectValue("birthMonth", "validation.date-empty"
                        , new String[]{"生年月日_月"}, "");
                return "input";
            case 3:
                //生年月日_日が空文字の場合のエラー処理
                result.rejectValue("birthDay", "validation.date-empty"
                        , new String[]{"生年月日_日"}, "");
                return "input";
            case 4:
                //生年月日の日付が不正な場合のエラー処理
                result.rejectValue("birthYear", "validation.date-invalidate");
                //生年月日_月・生年月日_日は、エラーフィールドの設定を行い、
                //メッセージを空文字に設定している
                result.rejectValue("birthMonth", "validation.empty-msg");
                result.rejectValue("birthDay", "validation.empty-msg");
                return "input";
            case 5:
                //生年月日の日付が未来日の場合のエラー処理
                result.rejectValue("birthYear", "validation.date-future");
                //生年月日_月・生年月日_日は、エラーフィールドの設定を行い、
                //メッセージを空文字に設定している
                result.rejectValue("birthMonth", "validation.empty-msg");
                result.rejectValue("birthDay", "validation.empty-msg");
                return "input";
            default:
                //性別が不正に書き換えられていないかチェックする
                if(!demoForm.getSexItems().keySet().contains(demoForm.getSex())){
                    result.rejectValue("sex", "validation.sex-invalidate");
                    return "input";
                }
                //エラーチェックに問題が無いので、正常時の画面遷移先に遷移
                return normalPath;
        }
    }
}