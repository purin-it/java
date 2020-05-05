package com.example.demo.check;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.StringUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.chrono.JapaneseChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Locale;

public class FutureDateValidator implements ConstraintValidator<FutureDate, Object> {

    /** 日付のフォーマット */
    private final static String dateFormat = "uuuuMMdd";

    /** アノテーションで指定した年・月・日・メッセージの項目名 */
    private String dtYear;
    private String dtMonth;
    private String dtDay;
    private String message;

    @Override
    public void initialize(FutureDate annotation) {
        //アノテーションで指定した年・月・日・メッセージの項目名を取得
        this.dtYear = annotation.dtYear();
        this.dtMonth = annotation.dtMonth();
        this.dtDay = annotation.dtDay();
        this.message = annotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        //BeanWrapperオブジェクトを生成
        BeanWrapper beanWrapper = new BeanWrapperImpl(value);
        //アノテーションで指定した年・月・日の項目値を取得
        String year = (String)beanWrapper.getPropertyValue(dtYear);
        String month = (String)beanWrapper.getPropertyValue(dtMonth);
        String day = (String)beanWrapper.getPropertyValue(dtDay);

        //年・月・日がすべて空白値の場合はtrueを返す
        if(StringUtils.isEmpty(year) && StringUtils.isEmpty(month)
                && StringUtils.isEmpty(day)){
            return true;
        }
        //年・月・日が未来日の場合は、エラーメッセージ・エラー項目を設定し
        //falseを返す。そうでなければtrueを返す
        String dateStr = year + addZero(month) + addZero(day);
        if(isFutureDate(dateStr, dateFormat)){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(dtYear)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

    /**
     * チェック対象文字列の日付が未来日かどうかを返す
     * @param dateStr チェック対象文字列
     * @param dateFormat 日付フォーマット
     * @return 日付が未来日かどうか
     */
    private static boolean isFutureDate(String dateStr, String dateFormat){
        if(StringUtils.isEmpty(dateStr) || StringUtils.isEmpty(dateFormat)){
            return false;
        }
        //日付と時刻を厳密に解決するスタイルで、暦体系は和暦体系で、
        //DateTimeFormatterオブジェクトを作成
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dateFormat, Locale.JAPAN)
                .withChronology(JapaneseChronology.INSTANCE)
                .withResolverStyle(ResolverStyle.STRICT);
        try{
            //日付の文字列が未来日の場合は、trueを返す
            //それ以外の、日付が不正な場合や過去日の場合は、falseを返す
            LocalDate dateStrDate = LocalDate.parse(dateStr, df);
            LocalDate now = LocalDate.now();
            if(dateStrDate.isAfter(now)){
                return true;
            }
            return false;
        }catch(Exception e){
            return false;
        }
    }

    /**
     * 数値文字列が1桁の場合、頭に0を付けて返す
     * @param intNum 数値文字列
     * @return 変換後数値文字列
     */
    private static String addZero(String intNum){
        if(StringUtils.isEmpty(intNum)){
            return intNum;
        }
        if(intNum.length() == 1){
            return "0" + intNum;
        }
        return intNum;
    }
}
