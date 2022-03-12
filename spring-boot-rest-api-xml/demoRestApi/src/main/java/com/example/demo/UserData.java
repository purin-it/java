package com.example.demo;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.example.demo.check.CheckDate;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * ユーザーデータテーブル(user_data)アクセス用エンティティ
 */
//テーブル名を「@Table」アノテーションで指定
//生年月日の日付チェックを「@CheckDate」アノテーションで指定
@Entity
@Table(name="user_data")
@Data
@CheckDate(dtYear="birthY", dtMonth="birthM", dtDay="birthD", message="{validation.date-invalidate}")
public class UserData {

    /** ID */
    //主キー項目に「@Id」を付与
    @Id
    private long id;

    /** 名前 */
    //必須チェックを「@NotEmpty」アノテーションで追加
    @NotEmpty(message="{validation.name-empty}")
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
    //性別の値チェック(1,2のいずれかであること)を「@Pattern」アノテーションで追加
    @Pattern(regexp="[1-2]", message="{validation.sex-invalidate}")
    private String sex;

    /** メモ */
    private String memo;
}