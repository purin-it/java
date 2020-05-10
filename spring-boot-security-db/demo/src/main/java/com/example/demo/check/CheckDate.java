package com.example.demo.check;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;

//RetentionPolicyはclassファイルに記録され実行時に参照できるモード(Runtime)とする
//JavaDoc指定対象(@Documented)とする
//バリデーションの実装クラスはCheckDateValidatorクラスとする
//本アノテーションによるチェック処理を、1クラスで複数回実施できるように設定
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy={CheckDateValidator.class})
@Repeatable(CheckDateAnnotation.class)
public @interface CheckDate {
    //表示するエラーメッセージ(アノテーション属性で指定)
    String message();
    //特定のバリデーショングループがカスタマイズできるような設定
    Class<?>[] groups() default {};
    //チェック対象のオブジェクトになんらかのメタ情報を与えるためだけの宣言
    Class<? extends Payload>[] payload() default {};

    //チェック対象の日付_年(アノテーション属性で指定)
    String dtYear();
    //チェック対象の日付_月(アノテーション属性で指定)
    String dtMonth();
    //チェック対象の日付_日(アノテーション属性で指定)
    String dtDay();
}
