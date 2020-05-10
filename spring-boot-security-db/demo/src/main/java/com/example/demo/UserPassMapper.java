package com.example.demo;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserPassMapper {

    /**
     * ユーザーパスワードデータテーブル(user_pass)からユーザー名をキーにデータを取得する
     * @param name ユーザー名
     * @return ユーザー名をもつデータ
     */
    UserPass findByName(String name);

    /**
     * 指定したユーザーパスワードデータテーブル(user_pass)のデータを追加する
     * @param userPass ユーザーパスワードデータテーブル(user_pass)の追加データ
     */
    void create(UserPass userPass);

    /**
     * 指定したユーザーパスワードデータテーブル(user_pass)のデータを更新する
     * @param userPass ユーザーパスワードデータテーブル(user_pass)の更新データ
     */
    void update(UserPass userPass);

}
