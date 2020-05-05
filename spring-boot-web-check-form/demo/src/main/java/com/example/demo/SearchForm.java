package com.example.demo;

import lombok.Data;
import javax.validation.constraints.AssertTrue;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Formオブジェクトのクラス
 */
@Data
public class SearchForm {

    /** 検索用名前 */
    private String searchName;

    /** 生年月日_年_from */
    private String fromBirthYear;

    /** 生年月日_月_from */
    private String fromBirthMonth;

    /** 生年月日_日_from */
    private String fromBirthDay;

    /** 生年月日_年_to */
    private String toBirthYear;

    /** 生年月日_月_to */
    private String toBirthMonth;

    /** 生年月日_日_to */
    private String toBirthDay;

    /** 検索用性別 */
    private String searchSex;

    /** 一覧画面の現在ページ数 */
    private int currentPageNum;

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
     * 生年月日_fromが不正でないかをチェックする
     * @return チェック結果
     */
    @AssertTrue(message = "{validation.date-invalidate-from}")
    public boolean isFromBirthDayInvalid(){
        return DateCheckUtil.checkSearchForm(this) != 1;
    }

    /**
     * 生年月日_toが不正でないかをチェックする
     * @return チェック結果
     */
    @AssertTrue(message = "{validation.date-invalidate-to}")
    public boolean isToBirthDayInvalid(){
        return DateCheckUtil.checkSearchForm(this) != 2;
    }

    /**
     * 生年月日_from＞生年月日_toでないかをチェックする
     * @return チェック結果
     */
    @AssertTrue(message = "{validation.date-invalidate-from-to}")
    public boolean isInvalidBirthDayFromTo(){
        return DateCheckUtil.checkSearchForm(this) != 3;
    }
}
