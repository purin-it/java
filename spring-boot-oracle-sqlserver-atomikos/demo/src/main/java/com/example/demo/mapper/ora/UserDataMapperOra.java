package com.example.demo.mapper.ora;

import com.example.demo.UserData;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDataMapperOra {

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
}