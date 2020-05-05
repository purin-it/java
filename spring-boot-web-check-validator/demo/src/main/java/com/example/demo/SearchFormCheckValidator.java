package com.example.demo;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SearchFormCheckValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        //チェック対象となるクラスがSearchFormクラスかどうかをチェックする
        return SearchForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object form, Errors errors) {
        SearchForm searchForm = (SearchForm) form;
        int checkDate = DateCheckUtil.checkSearchForm(searchForm);
        switch (checkDate){
            case 1:
                //生年月日_fromが不正な場合のエラー処理
                errors.rejectValue("fromBirthYear", "validation.date-invalidate-from");
                errors.rejectValue("fromBirthMonth", "validation.empty-msg");
                errors.rejectValue("fromBirthDay", "validation.empty-msg");
                break;
            case 2:
                //生年月日_toが不正な場合のエラー処理
                errors.rejectValue("toBirthYear", "validation.date-invalidate-to");
                errors.rejectValue("toBirthMonth", "validation.empty-msg");
                errors.rejectValue("toBirthDay", "validation.empty-msg");
                break;
            case 3:
                //生年月日_from＞生年月日_toの場合のエラー処理
                errors.rejectValue("fromBirthYear", "validation.date-invalidate-from-to");
                errors.rejectValue("fromBirthMonth", "validation.empty-msg");
                errors.rejectValue("fromBirthDay", "validation.empty-msg");
                errors.rejectValue("toBirthYear", "validation.empty-msg");
                errors.rejectValue("toBirthMonth", "validation.empty-msg");
                errors.rejectValue("toBirthDay", "validation.empty-msg");
                break;
            default:
                //正常の場合は何もしない
                break;
        }
    }
}
