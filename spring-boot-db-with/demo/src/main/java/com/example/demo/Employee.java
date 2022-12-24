package com.example.demo;

import lombok.Data;

import java.util.Date;

@Data
public class Employee {

    /** ID */
    private Integer id;

    /** 名前 */
    private String name;

    /** 生年月日 */
    private Date birthDay;

    /** 上司のID */
    private Integer bossId;
}
