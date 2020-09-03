package com.example.demo;

import org.thymeleaf.util.StringUtils;

//ユーティリティクラス
public class DemoUtil {

    /**
     * 改行コード
     */
    private static final String LINE_SEP = System.getProperty("line.separator");

    /**
     * 変換後の改行コード
     */
    private static final String ENCODE_LINE_SEP = "&#10";

    /**
     * 引数の文字列に改行コードが含まれている場合に、変換後の改行コードに変換する
     * @param str 任意の文字列
     * @return 変換後の文字列
     */
    public static String encodeNewLine(String str){
        if(StringUtils.isEmpty(str)){
            return str;
        }
        return str.replaceAll(LINE_SEP, ENCODE_LINE_SEP);
    }

    /**
     * 引数の文字列に変換後の改行コードが含まれている場合に、改行コードに変換する
     * @param str 任意の文字列
     * @return 変換後の文字列
     */
    public static String decodeNewLine(String str){
        if(StringUtils.isEmpty(str)){
            return str;
        }
        return str.replaceAll(ENCODE_LINE_SEP, LINE_SEP);
    }
}
