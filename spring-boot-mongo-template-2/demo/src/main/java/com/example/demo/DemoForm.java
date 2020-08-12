package com.example.demo;

import com.example.demo.check.CheckDate;
import com.example.demo.check.CheckZenkaku;
import com.example.demo.check.FutureDate;
import lombok.Data;
import org.thymeleaf.util.StringUtils;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

//日付チェック・未来日チェックを独自アノテーションで実施
@Data
@CheckDate(dtYear = "birthYear", dtMonth = "birthMonth", dtDay = "birthDay"
        , message = "{validation.date-invalidate}")
@FutureDate(dtYear = "birthYear", dtMonth = "birthMonth", dtDay = "birthDay"
        , message = "{validation.date-future}")
public class DemoForm implements Serializable {

    /** MongoDBの主キー */
    private String pKeyId;

    /** ID */
    private String id;

    /** 名前 */
    @NotEmpty
    @CheckZenkaku
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
     * 生年月日の年・月・日が入力されているかをチェックする
     * @return チェック結果
     */
    @AssertTrue(message = "{validation.date-empty}")
    public boolean isBirthDayRequired(){
        if(StringUtils.isEmpty(birthYear)
                && StringUtils.isEmpty(birthMonth)
                && StringUtils.isEmpty(birthDay)){
            return false;
        }
        return true;
    }

    /**
     * 性別が不正な値でないかチェックする
     * @return チェック結果
     */
    @AssertTrue(message = "{validation.sex-invalidate}")
    public boolean isSexInvalid(){
        return StringUtils.isEmpty(sex) || getSexItems().keySet().contains(sex);
    }
}
