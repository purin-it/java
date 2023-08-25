package common;

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
}
