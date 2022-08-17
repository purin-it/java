package com.example.demo;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDataMapper {

    /**
     * 指定したIDをもつユーザーデータテーブル(user_data)のデータを取得する
     * @param id ID
     * @return ユーザーデータテーブル(user_data)の指定したIDのデータ
     */
    UserData findById(Long id);

    /**
     * メモを条件にユーザーデータテーブル(user_data)のデータを取得する
     * @return ユーザーデータテーブル(user_data)のリスト
     */
    List<UserData> findByMemo();

    /**
     * 名前を条件にユーザーデータテーブル(user_data)のデータを取得する
     * @return ユーザーデータテーブル(user_data)のリスト
     */
    List<UserData> findByName();
}