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
     * 指定したユーザーデータテーブル(user_data)のデータを更新する
     * @param userData ユーザーデータテーブル(user_data)の更新データ
     */
    void update(UserData userData);

    /**
     * 指定したIDリストのIDにあてはまるユーザーデータテーブル(user_data)のデータを取得する
     * @param idList IDリスト
     * @return ユーザーデータテーブル(user_data)のリスト
     */
    List<UserData> findByIdList(List<Long> idList);
}