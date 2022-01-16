package com.example.demo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDataMapper {

    /**
     * 指定したユーザーデータテーブル(user_data)のデータのリストをまとめて追加する.
     * @param userDataList ユーザーデータテーブル(user_data)の追加データリスト
     */
    void insertMulti(@Param("userDataList") List<UserData> userDataList);

    /**
     * 指定したユーザーデータテーブル(user_data)のデータのリストをまとめて更新する.
     * @param userDataList ユーザーデータテーブル(user_data)の更新データリスト
     */
    void updateMulti(@Param("userDataList") List<UserData> userDataList);

    /**
     * ユーザーデータテーブル(user_data)のデータを全て取得する.
     * @return ユーザーデータテーブル(user_data)のデータリスト
     */
    List<UserData> findAll();
}