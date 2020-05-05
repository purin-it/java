package com.example.demo.check;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.StringUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class CheckDateValidator implements ConstraintValidator<CheckDate, Object> {

    /** 日付のフォーマット */
    private final static String dateFormat = "uuuuMMdd";

    /** アノテーションで指定した年・月・日・メッセージの項目名 */
    private String dtYear;
    private String dtMonth;
    private String dtDay;
    private String message;

    @Override
    public void initialize(CheckDate annotation) {
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
        //年・月・日が存在する日付でなければ、エラーメッセージ・エラー項目を設定し
        //falseを返す。そうでなければtrueを返す
        String dateStr = year + addZero(month) + addZero(day);
        if(!isCorrectDate(dateStr, dateFormat)){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(dtYear)
                    .addConstraintViolation();
            return false;
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
