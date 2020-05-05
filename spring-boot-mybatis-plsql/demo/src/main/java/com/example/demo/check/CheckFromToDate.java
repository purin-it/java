package com.example.demo.check;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Documented;

//アノテーションの付与対象をクラス(ElementType.TYPE)にする
//RetentionPolicyはclassファイルに記録され実行時に参照できるモード(Runtime)とする
//JavaDoc指定対象(@Documented)とする
//バリデーションの実装クラスはCheckFromToDateValidatorクラスとする
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy={CheckFromToDateValidator.class})
public @interface CheckFromToDate {
    //表示するエラーメッセージ(アノテーション属性で指定)
    String message();
    //特定のバリデーショングループがカスタマイズできるような設定
    Class<?>[] groups() default {};
    //チェック対象のオブジェクトになんらかのメタ情報を与えるためだけの宣言
    Class<? extends Payload>[] payload() default {};

    //チェック対象の日付_年_from(アノテーション属性で指定)
    String dtYearFrom();
    //チェック対象の日付_月_from(アノテーション属性で指定)
    String dtMonthFrom();
    //チェック対象の日付_日_from(アノテーション属性で指定)
    String dtDayFrom();
    //チェック対象の日付_年_to(アノテーション属性で指定)
    String dtYearTo();
    //チェック対象の日付_月_to(アノテーション属性で指定)
    String dtMonthTo();
    //チェック対象の日付_日_to(アノテーション属性で指定)
    String dtDayTo();
}
