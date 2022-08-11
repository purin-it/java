package com.example.demo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDataMapper {

    /**
     * ユーザーデータテーブル(user_data)の全データを削除する
     */
    void truncateData();

    /**
     * 指定した1件のユーザーデータテーブル(user_data)のデータを追加する
     * @param userData ユーザーデータテーブル(user_data)の追加データ
     */
    void insertDataOne(UserData userData);

    /**
     * 指定したユーザーデータテーブル(user_data)のデータのリストをまとめて追加する
     * @param userDataList ユーザーデータテーブル(user_data)の追加データリスト
     */
    void insertDataMulti(@Param("userDataList") List<UserData> userDataList);

    /**
     * ユーザーデータテーブル(user_data)のデータ件数を取得する
     * @return ユーザーデータテーブル(user_data)のデータ件数
     */
    Long countAll();
}