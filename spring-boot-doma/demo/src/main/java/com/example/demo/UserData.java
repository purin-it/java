package com.example.demo;

import lombok.Data;
import org.seasar.doma.Table;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Column;

/**
 * ユーザーデータテーブル(user_data)アクセス用エンティティ
 */
@Table(name = "user_data")
@Entity
@Data
public class UserData {

    /** ID */
    @Id
    private long id;

    /** 名前 */
    private String name;

    /** 生年月日_年 */
    @Column(name="birth_year")
    private int birthY;

    /** 生年月日_月 */
    @Column(name="birth_month")
    private int birthM;

    /** 生年月日_日 */
    @Column(name="birth_day")
    private int birthD;

    /** 性別 */
    private String sex;

}
