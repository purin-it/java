package com.example.demo;

import lombok.Data;
import javax.validation.constraints.NotEmpty;

/**
 * Formオブジェクトのクラス
 */
@Data
public class DemoForm {

    /** 名前 */
    @NotEmpty
    private String name;

    /** 生年月日_年 */
    private String birthYear;

    /** 生年月日_月 */
    private String birthMonth;

    /** 生年月日_日 */
    private String birthDay;

    /** 性別 */
    @NotEmpty
    private String sex;

    /** 確認チェック */
    @NotEmpty
    private String checked;

}