package com.example.demo;

import lombok.Data;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class DemoForm implements Serializable {

    /** ID */
    private String id;

    /** 名前 */
    private String name;

    /** 生年月日_年 */
    private String birthYear;

    /** 生年月日_月 */
    private String birthMonth;

    /** 生年月日_日 */
    private String birthDay;

    /** 性別 */
    private String sex;

    /** メモ */
    private String memo;

    /** 確認チェック */
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

}
