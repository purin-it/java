package com.example.demo;

import com.example.demo.order.First;
import com.example.demo.order.Second;
import com.example.demo.order.Third;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * Formオブジェクトのクラス
 */
@Data
public class DemoForm {

    /** ID */
    // 必須指定・桁数は3桁・半角数字で入力可能とする
    // 必須チェックは1回目に、それ以外のチェックは2回目に行う
    @NotEmpty(groups = First.class)
    @Length(min = 3, max = 3, groups = Second.class)
    @Pattern(regexp = "^[0-9]+$", message = "{validation.number}", groups = Second.class)
    private String id;

    /** パスワード */
    // 必須指定・桁数は8～10桁・半角英数字で入力可能とする
    // 必須チェックは1回目に、桁数チェックは2回目に、半角英数字チェックは3回目にそれぞれ行う
    @NotEmpty(groups = First.class)
    @Length(min = 8, max = 10, groups = Second.class)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "{validation.alpha-number}", groups = Third.class)
    private String password;

}