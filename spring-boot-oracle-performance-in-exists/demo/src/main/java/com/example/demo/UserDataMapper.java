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
     * IN句を使って、ユーザーデータテーブル(user_data)の生年月日が基準日と
     * 一致するデータを全件取得する
     * @param basicDate 基準日
     * @return ユーザーデータテーブル(user_data)のリスト
     */
    List<UserData> findByBirthdayIn(Date basicDate);

    /**
     * EXISTS句を使って、ユーザーデータテーブル(user_data)の生年月日が基準日と
     * 一致するデータを全件取得する
     * @param basicDate 基準日
     * @return ユーザーデータテーブル(user_data)のリスト
     */
    List<UserData> findByBirthdayExists(Date basicDate);

}