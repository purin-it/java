package common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 共通ユーティリティクラス.
 */
public class CommonUtil {

    /**
     * 引数で指定した文字列がNULL,空文字,空白のみかどうかをチェックする.
     * @param cs 文字列
     * @return 判定結果
     */
    public static boolean isBlank(final CharSequence cs) {
        final int strLen = length(cs);
        if (strLen == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 引数で指定した文字列の長さを返却する.
     * @param cs 文字列
     * @return 文字列の長さ
     */
    public static int length(final CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    /**
     * DateTimeFormatterを利用して日付チェックを行う.
     * @param dateStr チェック対象文字列
     * @param dateFormat 日付フォーマット
     * @return 日付チェック結果
     */
    public static boolean isCorrectDate(String dateStr, String dateFormat){
        if(CommonUtil.isBlank(dateStr) || CommonUtil.isBlank(dateFormat)){
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
     * 数値文字列が1桁の場合、頭に0を付けて返す.
     * @param intNum 数値文字列
     * @return 変換後数値文字列
     */
    public static String addZero(String intNum){
        if(CommonUtil.isBlank(intNum)){
            return intNum;
        }
        if(intNum.length() == 1){
            return "0" + intNum;
        }
        return intNum;
    }

    /**
     * 引数のメッセージKeyに該当するメッセージを取得する.
     * @param messageKey メッセージKey
     * @return メッセージ
     */
    public static String getMessage(String messageKey){
        ResourceBundle bundle = null;
        String value = null;
        try{
            bundle = ResourceBundle.getBundle("org.hibernate.validator.ValidationMessages_ja");
            value = bundle.getString(messageKey);
        }catch(MissingResourceException ex){
            System.err.println(ex);
            return null;
        }
        return value;
    }
}
