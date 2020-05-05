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
//バリデーションの実装クラスはFutureDateValidatorクラスとする
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy={FutureDateValidator.class})
public @interface FutureDate {
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
