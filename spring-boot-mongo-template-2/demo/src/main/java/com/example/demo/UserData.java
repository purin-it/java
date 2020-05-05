package com.example.demo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//コレクション名を@Documentアノテーションで指定
@Data
@Document(collection = "user_data")
public class UserData {

    /** MongoDBの主キー(変数名は任意) */
    @Id
    private String pKeyId;

    /** ID */
    private long id;

    /** 名前 */
    private String name;

    /** 生年月日_年 */
    private int birth_year;

    /** 生年月日_月 */
    private int birth_month;

    /** 生年月日_日 */
    private int birth_day;

    /** 性別 */
    private int sex;

    /** メモ */
    private String memo;

}
