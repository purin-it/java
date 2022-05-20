package com.example.demo.mapper.ora;

import com.example.demo.mapper.UserData;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface UserDataMapperOra {

    /**
     * Oracleでユーザーデータテーブル(user_data)の全データを取得する
     * @return ユーザーデータテーブル(user_data)の全データ
     */
    List<UserData> findAll();

    /**
     * Oracleでユーザーデータテーブル(user_data)の全データを削除する
     */
    void truncateUserData();
}