package com.example.demo;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DemoFormCheckValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        //チェック対象となるクラスがDemoFormクラスかどうかをチェックする
        return DemoForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object form, Errors errors) {
        DemoForm demoForm = (DemoForm) form;
        //DemoForm内の単項目チェックでエラーだった場合は何もしない
        if(errors.hasErrors()){
            return;
        }
        //生年月日の日付チェック処理を行う
        //エラーがある場合は、エラーメッセージ・エラーフィールドの設定を行う
        int checkDate = DateCheckUtil.checkDate(demoForm.getBirthYear()
                , demoForm.getBirthMonth(), demoForm.getBirthDay());
        switch(checkDate){
            case 1:
                //生年月日_年が空文字の場合のエラー処理
                errors.rejectValue("birthYear", "validation.date-empty"
                        , new String[]{"生年月日_年"}, "");
                break;
            case 2:
                //生年月日_月が空文字の場合のエラー処理
                errors.rejectValue("birthMonth", "validation.date-empty"
                        , new String[]{"生年月日_月"}, "");
                break;
            case 3:
                //生年月日_日が空文字の場合のエラー処理
                errors.rejectValue("birthDay", "validation.date-empty"
                        , new String[]{"生年月日_日"}, "");
                break;
            case 4:
                //生年月日の日付が不正な場合のエラー処理
                errors.rejectValue("birthYear", "validation.date-invalidate");
                //生年月日_月・生年月日_日は、エラーフィールドの設定を行い、
                //メッセージを空文字に設定している
                errors.rejectValue("birthMonth", "validation.empty-msg");
                errors.rejectValue("birthDay", "validation.empty-msg");
                break;
            case 5:
                //生年月日の日付が未来日の場合のエラー処理
                errors.rejectValue("birthYear", "validation.date-future");
                //生年月日_月・生年月日_日は、エラーフィールドの設定を行い、
                //メッセージを空文字に設定している
                errors.rejectValue("birthMonth", "validation.empty-msg");
                errors.rejectValue("birthDay", "validation.empty-msg");
                break;
            default:
                //性別が不正に書き換えられていないかチェックする
                //正常な場合は何もしない
                if(!demoForm.getSexItems().keySet().contains(demoForm.getSex())) {
                    errors.rejectValue("sex", "validation.sex-invalidate");
                }
                break;
        }
    }
}
