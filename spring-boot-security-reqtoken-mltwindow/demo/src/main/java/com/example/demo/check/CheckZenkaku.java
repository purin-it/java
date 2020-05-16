package com.example.demo.check;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Documented;

//アノテーションの付与対象をフィールド(ElementType.FIELD)にする
//RetentionPolicyはclassファイルに記録され実行時に参照できるモード(Runtime)とする
//JavaDoc指定対象(@Documented)とする
//バリデーションの実装クラスはCheckDateValidatorクラスとする
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy={CheckZenkakuValidator.class})
public @interface CheckZenkaku {
    //表示するエラーメッセージ(アノテーション属性で指定)
    String message() default "{validation.zenkaku}";
    //特定のバリデーショングループがカスタマイズできるような設定
    Class<?>[] groups() default {};
    //チェック対象のオブジェクトになんらかのメタ情報を与えるためだけの宣言
    Class<? extends Payload>[] payload() default {};
}
