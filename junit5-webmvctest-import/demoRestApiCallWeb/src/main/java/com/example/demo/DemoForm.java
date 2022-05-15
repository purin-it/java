package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.util.StringUtils;

import com.example.demo.check.CheckDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@CheckDate(dtYear = "birthY", dtMonth = "birthM", dtDay = "birthD"
, message = "{validation.date-invalidate}")
public class DemoForm {

    /** ID */
	@Pattern(regexp = "^[1-9][0-9]*$", message = "{validation.id-positive-int}")
    private String id;

    /** 名前 */
    @NotEmpty
    private String name;

    /** 生年月日_年 */
    private String birthY;

    /** 生年月日_月 */
    private String birthM;

    /** 生年月日_日 */
    private String birthD;

    /** 性別 */
    private String sex;

    /** メモ */
    private String memo;

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
        if(!StringUtils.hasText(birthY)
                && !StringUtils.hasText(birthM)
                && !StringUtils.hasText(birthD)){
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
        return !StringUtils.hasText(sex) 
        		|| getSexItems().keySet().contains(sex);
    }

}
