package com.example.demo;

import lombok.Data;
import javax.validation.constraints.Pattern;

/**
 * Formオブジェクトのクラス
 */
@Data
public class DemoForm {

    // 半角英小文字を含む(肯定先読みを使わない)
    // 正規表現「.*」は、任意の0文字以上の文字列にマッチする
    @Pattern(regexp = "^(.*[a-z].*)$", message = "{validation.include-lowercase}")
    private String text1;

    // 半角英小文字を含む(肯定先読みを使う)
    @Pattern(regexp = "^(?=.*[a-z]).*$", message = "{validation.include-lowercase}")
    private String text2;

    // 半角英大文字と半角英小文字を含む(肯定先読みを使う)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).*$", message = "{validation.include-lower-upper-case}")
    private String text3;

    // 半角英大文字と半角英小文字と数字を含む(肯定先読みを使う)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*$", message = "{validation.include-lower-upper-case-number}")
    private String text4;

}