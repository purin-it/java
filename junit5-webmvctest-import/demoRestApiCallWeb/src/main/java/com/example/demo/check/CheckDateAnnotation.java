package com.example.demo.check;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//CheckDateアノテーションチェック処理を
//1クラスで複数回実施できるように設定
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckDateAnnotation {
    CheckDate[] value();
}
