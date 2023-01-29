package com.example.demo.check;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.StringUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class CheckFromToDateValidator implements ConstraintValidator<CheckFromToDate, Object> {

    /** 日付のフォーマット */
    private final static String dateFormat = "uuuuMMdd";

    /** アノテーションで指定した年・月・日・メッセージの項目名 */
    private String dtYearFrom;
    private String dtMonthFrom;
    private String dtDayFrom;
    private String dtYearTo;
    private String dtMonthTo;
    private String dtDayTo;
    private String message;

    @Override
    public void initialize(CheckFromToDate annotation) {
        //アノテーションで指定したfrom,toの年・月・日・メッセージの項目名を取得
        this.dtYearFrom = annotation.dtYearFrom();
        this.dtMonthFrom = annotation.dtMonthFrom();
        this.dtDayFrom = annotation.dtDayFrom();
        this.dtYearTo = annotation.dtYearTo();
        this.dtMonthTo = annotation.dtMonthTo();
        this.dtDayTo = annotation.dtDayTo();
        this.message = annotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        //BeanWrapperオブジェクトを生成
        BeanWrapper beanWrapper = new BeanWrapperImpl(value);
        //アノテーションで指定したfrom,toの年・月・日の項目値を取得
        String yearFrom = (String)beanWrapper.getPropertyValue(dtYearFrom);
        String monthFrom = (String)beanWrapper.getPropertyValue(dtMonthFrom);
        String dayFrom = (String)beanWrapper.getPropertyValue(dtDayFrom);
        String yearTo = (String)beanWrapper.getPropertyValue(dtYearTo);
        String monthTo = (String)beanWrapper.getPropertyValue(dtMonthTo);
        String dayTo = (String)beanWrapper.getPropertyValue(dtDayTo);

        //from,toの年・月・日の年月日がすべて入力されている場合
        if(!StringUtils.isEmpty(yearFrom) && !StringUtils.isEmpty(monthFrom)
          && !StringUtils.isEmpty(dayFrom) && !StringUtils.isEmpty(yearTo)
          && !StringUtils.isEmpty(monthTo) && !StringUtils.isEmpty(dayTo)){

            //年月日_from, 年月日_toを生成
            String fromDay = yearFrom + addZero(monthFrom) + addZero(dayFrom);
            String toDay = yearTo + addZero(monthTo) + addZero(dayTo);

            //年月日_from,年月日_toがいずれも存在する日付の場合
            if(isCorrectDate(fromDay, dateFormat) && isCorrectDate(toDay, dateFormat)){

                //年月日_from＞年月日_toの場合は、エラーメッセージ・エラー項目を設定し
                //falseを返す。そうでなければtrueを返す
                if(fromDay.compareTo(toDay) > 0){
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(message)
                            .addPropertyNode(dtYearFrom)
                            .addConstraintViolation();
                    context.buildConstraintViolationWithTemplate("")
                            .addPropertyNode(dtYearTo)
                            .addConstraintViolation();
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * DateTimeFormatterを利用して日付チェックを行う
     * @param dateStr チェック対象文字列
     * @param dateFormat 日付フォーマット
     * @return 日付チェック結果
     */
    private static boolean isCorrectDate(String dateStr, String dateFormat){
        if(StringUtils.isEmpty(dateStr) || StringUtils.isEmpty(dateFormat)){
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
