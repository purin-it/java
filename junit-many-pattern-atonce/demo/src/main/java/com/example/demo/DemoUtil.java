package com.example.demo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class DemoUtil {

    /**
     * 日付チェック処理を行う
     * @param year　年
     * @param month 月
     * @param day 日
     * @return 判定結果(1:年が空、2:月が空、3:日が空、4:年月日が不正、0:正常
     */
    public static int checkDate(String year, String month, String day){
        final String dateFormat = "uuuuMMdd";
        if(isEmpty(year)){
            return 1;
        }
        if(isEmpty(month)){
            return 2;
        }
        if(isEmpty(day)){
            return 3;
        }
        String dateStr = year + addZero(month) + addZero(day);
        if(!isCorrectDate(dateStr, dateFormat)){
            return 4;
        }
        return 0;
    }

    /**
     * 数値文字列が1桁の場合、頭に0を付けて返す
     * @param intNum 数値文字列
     * @return 変換後数値文字列
     */
    public static String addZero(String intNum){
        if(isEmpty(intNum)){
            return intNum;
        }
        if(intNum.length() == 1){
            return "0" + intNum;
        }
        return intNum;
    }

    /**
     * 引数の文字列がnull、空文字かどうかを判定する
     * @param str チェック対象文字列
     * @return 文字列チェック結果
     */
    public static boolean isEmpty(String str){
        if(str == null || "".equals(str)){
            return true;
        }
        return false;
    }

    /**
     * DateTimeFormatterを利用して日付チェックを行う
     * @param dateStr チェック対象文字列
     * @param dateFormat 日付フォーマット
     * @return 日付チェック結果
     */
    private static boolean isCorrectDate(String dateStr, String dateFormat){
        if(isEmpty(dateStr) || isEmpty(dateFormat)){
            return false;
        }
        //日付と時刻を厳密に解決するスタイルで、DateTimeFormatterオブジェクトを作成
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dateFormat)
                .withResolverStyle(ResolverStyle.STRICT);
        try{
            //チェック対象文字列をLocalDate型の日付に変換できれば、チェックOKとする
            LocalDate.parse(dateStr, df);
            return true;
        }catch(Exception e){
            return false;
        }
    }

}
