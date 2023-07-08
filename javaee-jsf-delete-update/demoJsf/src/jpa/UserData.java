package jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * USER_DATAテーブルのエンティティ.
 */
@Entity
@Table(name="user_data")
@Data
public class UserData implements Serializable {

    // シリアルバージョンUID
    private static final long serialVersionUID = -5319751026833041042L;

    /** ID */
    @Id
    private Integer id;

    /** 名前 */
    private String name;

    /** 生年月日_年 */
    @Column(name = "birth_year")
    private Integer birthYear;

    /** 生年月日_月 */
    @Column(name = "birth_month")
    private Integer birthMonth;

    /** 生年月日_日 */
    @Column(name = "birth_day")
    private Integer birthDay;

    /** 性別 */
    private String sex;

    /** メモ */
    private String memo;

}
