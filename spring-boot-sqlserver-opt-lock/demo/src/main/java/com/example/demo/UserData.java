package com.example.demo;

import lombok.Data;

/**
 * ユーザーデータテーブル(user_data)アクセス用エンティティ
 */
@Data
public class UserData {

    /** ID */
    private long id;

    /** 名前 */
    private String name;

    /** 生年月日_年 */
    private int birthY;

    /** 生年月日_月 */
    private int birthM;

    /** 生年月日_日 */
    private int birthD;

    /** 性別 */
    private String sex;

    /** 性別(文字列) */
    private String sex_value;

    /** バージョン */
    private int version;

}
