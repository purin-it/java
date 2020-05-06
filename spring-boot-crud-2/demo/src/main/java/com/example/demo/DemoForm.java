package com.example.demo;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotEmpty;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Formオブジェクトのクラス
 */
public class DemoForm {

    /** ID */
    @Getter
    @Setter
    private String id;

    /** 名前 */
    @Getter
    @Setter
    @NotEmpty
    private String name;

    /** 生年月日_年 */
    @Getter
    @Setter
    private String birthYear;

    /** 生年月日_月 */
    @Getter
    @Setter
    private String birthMonth;

    /** 生年月日_日 */
    @Getter
    @Setter
    private String birthDay;

    /** 性別 */
    @Getter
    @Setter
    @NotEmpty
    private String sex;

    /** 確認チェック */
    @Getter
    @Setter
    @NotEmpty
    private String checked;

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

}