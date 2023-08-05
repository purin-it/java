package common;

/**
 * コード変換を行うユーティリティクラス.
 */
public class CodeConvUtil {

    // 性別をenumで定義
    private enum Sex {
        MAN("1", "男"),
        WOMAN("2", "女");

        // enumのフィールドを定義
        private final String code;
        private final String value;

        // enumのコンストラクタを定義
        private Sex(String code, String value){
            this.code = code;
           this.value = value;
        }
    }

    /**
     * 引数の性別のコードから性別の値を取得する.
     * @param sex 性別のコード
     * @return 性別の値
     */
    public static String getSexValue(String sex){
        String retVal = null;

        // 性別が男の場合
        if(Sex.MAN.code.equals(sex)){
            retVal = Sex.MAN.value;
        // 性別が女の場合
        }else if(Sex.WOMAN.code.equals(sex)){
            retVal = Sex.WOMAN.value;
        // 性別が上記以外の場合
        }else{
            retVal = "不明";
        }

        return retVal;
    }

}
