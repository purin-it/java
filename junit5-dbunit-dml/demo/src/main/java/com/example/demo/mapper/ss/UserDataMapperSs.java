package com.example.demo.mapper.ss;

import com.example.demo.mapper.UserData;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface UserDataMapperSs {

    /**
     * SQL Serverでユーザーデータテーブル(user_data)の全データを取得する
     * @return ユーザーデータテーブル(user_data)の全データ
     */
    List<UserData> findAll();

    /**
     * SQL Serverでユーザーデータテーブル(user_data)の全データを削除する
     */
    void truncateUserData();

    /**
     * SQL Serverで指定したユーザーデータテーブル(user_data)のデータを追加する
     * @param userData ユーザーデータテーブル(user_data)の追加データ
     */
    void create(UserData userData);

    /**
     * SQL Serverで指定したユーザーデータテーブル(user_data)のデータを更新する
     * @param userData ユーザーデータテーブル(user_data)の更新後データ
     */
    void update(UserData userData);

    /**
     * SQL Serverで指定したIDをもつユーザーデータテーブル(user_data)のデータを削除する
     * @param id ID
     * @return ユーザーデータテーブル(user_data)の指定したIDのデータ
     */
    void deleteById(Long id);
}