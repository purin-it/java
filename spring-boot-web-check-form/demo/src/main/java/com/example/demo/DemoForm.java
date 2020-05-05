package com.example.demo;

import lombok.Data;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Formオブジェクトのクラス
 */
@Data
public class DemoForm implements Serializable {

    /** ID */
    private String id;

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

    /** メモ */
    private String memo;

    /** 確認チェック */
    @NotEmpty
    private String checked;

    /** 性別(文字列) */
    private String sex_value;

    /** 生年月日_月のMapオブジェクト */
    public Map<String,String> getMonthItems(){
        Map<String, String> monthMap = new LinkedHashMap<String, String>();
        for(int i = 1; i <= 12; i++){
            monthMap.put(String.valueOf(i), String.valueOf(i));
        }
        return monthMap;
    }

    /** 生年月日_日のMapオブジェクト */
    public Map<String,String> getDayItems(){
        Map<String, String> dayMap = new LinkedHashMap<String, String>();
        for(int i = 1; i <= 31; i++){
            dayMap.put(String.valueOf(i), String.valueOf(i));
        }
        return dayMap;
    }

    /** 性別のMapオブジェクト */
    public Map<String,String> getSexItems(){
        Map<String, String> sexMap = new LinkedHashMap<String, String>();
        sexMap.put("1", "男");
        sexMap.put("2", "女");
        return sexMap;
    }

    /**
     * 生年月日_年が空文字でないかをチェックする
     * @return チェック結果
     */
    @AssertTrue(message = "{validation.date-empty}")
    public boolean isBirthYearEmpty(){
        return DateCheckUtil.checkDate(birthYear, birthMonth, birthDay) != 1;
    }

    /**
     * 生年月日_月が空文字でないかをチェックする
     * @return チェック結果
     */
    @AssertTrue(message = "{validation.date-empty}")
    public boolean isBirthMonthEmpty(){
        return DateCheckUtil.checkDate(birthYear, birthMonth, birthDay) != 2;
    }

    /**
     * 生年月日_日が空文字でないかをチェックする
     * @return チェック結果
     */
    @AssertTrue(message = "{validation.date-empty}")
    public boolean isBirthDayEmpty(){
        return DateCheckUtil.checkDate(birthYear, birthMonth, birthDay) != 3;
    }

    /**
     * 生年月日の日付が不正でないかをチェックする
     * @return チェック結果
     */
    @AssertTrue(message = "{validation.date-invalidate}")
    public boolean isBirthDayInvalid(){
        return DateCheckUtil.checkDate(birthYear, birthMonth, birthDay) != 4;
    }

    /**
     * 生年月日の日付が未来日でないかをチェックする
     * @return チェック結果
     */
    @AssertTrue(message = "{validation.date-future}")
    public boolean isBirthDayFuture(){
        return DateCheckUtil.checkDate(birthYear, birthMonth, birthDay) != 5;
    }

}
