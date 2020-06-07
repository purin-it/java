package com.example.demo;

import lombok.Data;

@Data
public class UserPass {

    /**
     * 指定したユーザー名・パスワード・権限をもつUserPassオブジェクトを作成する
     * @param name ユーザー名
     * @param pass パスワード
     * @param auth 権限
     */
    public UserPass(String name, String pass, String auth){
        this.name = name;
        this.pass = pass;
        this.auth = auth;
    }

    /** ユーザー名 */
    private String name;

    /** パスワード */
    private String pass;

    /** 権限 */
    private String auth;

}
