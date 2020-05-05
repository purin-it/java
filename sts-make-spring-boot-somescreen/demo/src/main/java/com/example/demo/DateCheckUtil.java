package com.example.demo;

import java.time.LocalDate;
import java.time.chrono.JapaneseChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Locale;

public class DateCheckUtil {

    /** 日付のフォーマット */
    private final static String dateFormat = "uuuuMMdd";

    /**
     * 日付チェック処理を行う
     * @param year　年
     * @param month 月
     * @param day 日
     * @return 判定結果(1:年が空、2:月が空、3:日が空、4:年月日が不正、5:年月日が未来日、0:正常)
     */
    public static int checkDate(String year, String month, String day){
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
        if(isFutureDate(dateStr, dateFormat)){
            return 5;
        }
        return 0;
    }

    /**
     * 検索用Formオブジェクトのチェック処理行う
     * @param searchForm 検索用Formオブジェクト
     * @return 判定結果(1:生年月日_fromが不正、2:生年月日_toが不正、3:生年月日_from＞生年月日_to、0:正常)
     */
    public static int checkSearchForm(SearchForm searchForm){
        //生年月日_fromが不正な場合
        if(!checkSearchFormBirthday(searchForm.getFromBirthYear()
                , searchForm.getFromBirthMonth(), searchForm.getFromBirthDay())){
            return 1;
        }
        //生年月日_toが不正な場合
        if(!checkSearchFormBirthday(searchForm.getToBirthYear()
                , searchForm.getToBirthMonth(), searchForm.getToBirthDay())){
            return 2;
        }
        //生年月日_from＞生年月日_toの場合
        if(!isEmpty(searchForm.getFromBirthYear()) && !isEmpty(searchForm.getToBirthYear())){
            String fromBirthDay = searchForm.getFromBirthYear()
                    + addZero(searchForm.getFromBirthMonth()) + addZero(searchForm.getFromBirthDay());
            String toBirthDay = searchForm.getToBirthYear()
                    + addZero(searchForm.getToBirthMonth()) + addZero(searchForm.getToBirthDay());
            if(fromBirthDay.compareTo(toBirthDay) > 0){
                return 3;
            }
        }
        //正常な場合
        return 0;
    }

    /**
     * 検索用Form内の日付をチェックする
     * @param year 年
     * @param month 月
     * @param day 日
     * @return 日付チェック結果
     */
    private static boolean checkSearchFormBirthday(String year, String month, String day){
        //年・月・日が全て未指定の場合はチェックOKとする
        if(isEmpty(year) && isEmpty(month) && isEmpty(day)){
            return true;
        }
        //年・月・日が全て指定されている場合は、日付が正しい場合にチェックOKとする
        if(!isEmpty(year) && !isEmpty(month) && !isEmpty(day)){
            String dateStr = year + addZero(month) + addZero(day);
            if(isCorrectDate(dateStr, dateFormat)){
                return true;
            }
            return false;
        }
        //年・月・日が指定あり/指定なしで混在している場合はチェックNGとする
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

    /**
     * 日付の文字列が未来日かどうかを判定する
     * @param dateStr チェック対象文字列
     * @param dateFormat 日付フォーマット
     * @return 判定結果
     */
    private static boolean isFutureDate(String dateStr, String dateFormat){
        if(!isCorrectDate(dateStr, dateFormat)){
            return false;
        }
        LocalDate dateStrDate = convertStrToLocalDate(dateStr, dateFormat);
        LocalDate now = LocalDate.now();
        if(dateStrDate.isAfter(now)){
            return true;
        }
        return false;
    }

    /**
     * 日付の文字列を日付型に変換した結果を返す
     * @param dateStr 日付の文字列
     * @param dateFormat 日付のフォーマット
     * @return 変換後の文字列
     */
    private static LocalDate convertStrToLocalDate(String dateStr, String dateFormat){
        if(isEmpty(dateStr) || isEmpty(dateFormat)){
            return null;
        }
        //日付と時刻を厳密に解決するスタイルで、暦体系は和暦体系で、DateTimeFormatterオブジェクトを作成
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dateFormat, Locale.JAPAN)
                .withChronology(JapaneseChronology.INSTANCE).withResolverStyle(ResolverStyle.STRICT);
        //日付の文字列をLocalDate型に変換して返却
        return LocalDate.parse(dateStr, df);
    }

    /**
     * 数値文字列が1桁の場合、頭に0を付けて返す
     * @param intNum 数値文字列
     * @return 変換後数値文字列
     */
    private static String addZero(String intNum){
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
}
