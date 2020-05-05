package com.example.demo;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;

//テーブル名を「@Table」アノテーションで指定
//「@Data」アノテーションを付与すると、このクラス内の全フィールドに対する
//Getterメソッド・Setterメソッドにアクセスができる
@Entity
@Table(name="user_data")
@Data
public class UserData {

    /** ID */
    //主キー項目に「@Id」を付与
    //その値を自動生成しないため「@GeneratedValue」は付与しない
    @Id
    private long id;

    /** 名前 */
    private String name;

    /** 生年月日_年 */
    //カラム名を「@Column」アノテーションで指定
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

    /** メモ */
    private String memo;
}