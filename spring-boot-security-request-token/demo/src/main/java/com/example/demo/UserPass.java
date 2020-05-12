package com.example.demo;

import lombok.Data;

@Data
public class UserPass {

    /**
     * 指定したユーザー名・パスワードをもつUserPassオブジェクトを作成する
     * @param name ユーザー名
     * @param pass パスワード
     */
    public UserPass(String name, String pass){
        this.name = name;
        this.pass = pass;
    }

    /** ユーザー名 */
    private String name;

    /** パスワード */
    private String pass;

}
