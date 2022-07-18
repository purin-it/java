package com.example.demo;

import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
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
     * ユーザーデータテーブル(user_data)から生年月日が基準日前後10日間のデータを、
     * BETWEEN句を使わないで全件取得する
     * @param basicDate 基準日
     * @return ユーザーデータテーブル(user_data)のリスト
     */
    List<UserData> findByBirthdayNotBetween(Date basicDate);

    /**
     * ユーザーデータテーブル(user_data)から生年月日が基準日前後10日間のデータを、
     * BETWEEN句を使って全件取得する
     * @param basicDate 基準日
     * @return ユーザーデータテーブル(user_data)のリスト
     */
    List<UserData> findByBirthdayBetween(Date basicDate);
}