package com.example.demo.mapper.secondary;

import com.example.demo.UserData;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDataMapperSecondary {

    /**
     * 指定したIDをもつユーザーデータテーブル(user_data)のデータを削除する
     * @param id ID
     */
    void deleteById(Long id);

    /**
     * 指定したユーザーデータテーブル(user_data)のデータを追加する
     * @param userData ユーザーデータテーブル(user_data)の追加データ
     */
    void create(UserData userData);

    /**
     * 指定したユーザーデータテーブル(user_data)のデータを更新する
     * @param userData ユーザーデータテーブル(user_data)の更新データ
     */
    void update(UserData userData);

}
