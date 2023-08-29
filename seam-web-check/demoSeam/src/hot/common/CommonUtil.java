package common;

import java.text.SimpleDateFormat;

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
     * SimpleDateFormatを利用して日付チェックを行う.
     * @param dateStr チェック対象文字列
     * @param dateFormat 日付フォーマット
     * @return 日付チェック結果
     */
    public static boolean isCorrectDate(String dateStr, String dateFormat){
        if(CommonUtil.isBlank(dateStr) || CommonUtil.isBlank(dateFormat)){
            return false;
        }
        // JDK6なので、SimpleDateFormatクラスを利用して日付を変換する
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);
        try {
            // チェック対象文字列をDate型の日付に変換できれば、チェックOKとする
            sdf.parse(dateStr);
            return true;
        } catch(Exception e) {
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

}
