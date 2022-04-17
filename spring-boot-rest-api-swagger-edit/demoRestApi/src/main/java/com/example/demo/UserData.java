package com.example.demo;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.example.demo.check.CheckDate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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
@ApiModel(description="ユーザー情報")
public class UserData {

    /** ID */
    //主キー項目に「@Id」を付与
    @Id
    @ApiModelProperty(value="ユーザー情報を識別するID", example="1")
    private long id;

    /** 名前 */
    //必須チェックを「@NotEmpty」アノテーションで追加
    @NotEmpty(message="{validation.name-empty}")
    @ApiModelProperty(value="名前", example="テスト　プリン１", required=true)
    private String name;

    /** 生年月日_年 */
    //カラム名を「@Column」アノテーションで指定
    @Column(name="birth_year")
    @ApiModelProperty(value="生年月日_年", example="2006")
    private int birthY;

    /** 生年月日_月 */
    @Column(name="birth_month")
    @ApiModelProperty(value="生年月日_月", example="12")
    private int birthM;

    /** 生年月日_日 */
    @Column(name="birth_day")
    @ApiModelProperty(value="生年月日_日", example="10")
    private int birthD;

    /** 性別 */
    //性別の値チェック(1,2のいずれかであること)を「@Pattern」アノテーションで追加
    @Pattern(regexp="[1-2]", message="{validation.sex-invalidate}")
    @ApiModelProperty(value="性別", example="2", required=true, allowableValues="range[1,2]")
    private String sex;

    /** メモ */
    @ApiModelProperty(value="メモ", example="テスト１", required=false)
    private String memo;
}