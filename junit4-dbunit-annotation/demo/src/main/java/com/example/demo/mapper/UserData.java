package com.example.demo.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ユーザーデータテーブル(user_data)アクセス用エンティティ
 */
@Data
// @NoArgsConstructorアノテーションで引数のない
// コンストラクタを、@AllArgsConstructorアノテーションで
// 全てのフィールドを引数に持つコンストラクタを定義している
@NoArgsConstructor
@AllArgsConstructor
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

    /** メモ */
    private String memo;

    /** 性別(文字列) */
    private String sex_value;

}
