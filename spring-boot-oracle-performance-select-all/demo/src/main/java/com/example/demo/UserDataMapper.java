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
     * select * によってユーザーデータテーブル(user_data)のデータを全件取得する
     * @return ユーザーデータテーブル(user_data)のリスト
     */
    List<UserData> findAllByAsta();

    /**
     * select (カラム名) によってユーザーデータテーブル(user_data)のデータを全件取得する
     * @return ユーザーデータテーブル(user_data)のリスト
     */
    List<UserData> findAllByColumn();

    /**
     * select count(*) によってユーザーデータテーブル(user_data)のデータ件数を取得する
     * @return ユーザーデータテーブル(user_data)のデータ件数
     */
    Long countAllByAsta();

    /**
     * select count((カラム名)) によってユーザーデータテーブル(user_data)のデータ件数を取得する
     * @return ユーザーデータテーブル(user_data)のデータ件数
     */
    Long countAllByColumn();
}