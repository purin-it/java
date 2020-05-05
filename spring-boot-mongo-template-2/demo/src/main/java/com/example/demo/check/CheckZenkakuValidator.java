package com.example.demo.check;

import org.springframework.util.StringUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckZenkakuValidator implements ConstraintValidator<CheckZenkaku, String> {

    /**
     * 全角チェックを行うための正規表現
     * (コンパイルに時間がかかるためあらかじめ定数化しておく)
     */
    private static Pattern pattern = Pattern.compile("^[^!-~｡-ﾟ]*$");

    @Override
    public void initialize(CheckZenkaku annotation) {
        //初期化処理は不要
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //valueには、アノテーションを付与したフィールドの項目値が設定される
        //valueが空白値か、全て全角文字の場合はtrueを返すチェック処理を行う
        if(StringUtils.isEmpty(value)){
            return true;
        }
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }

}
