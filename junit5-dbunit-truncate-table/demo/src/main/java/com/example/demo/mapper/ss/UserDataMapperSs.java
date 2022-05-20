package com.example.demo.mapper.ss;

import com.example.demo.mapper.UserData;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface UserDataMapperSs {

    /**
     * SQL Serverでユーザーデータテーブル(user_data)の全データを取得する
     * @return ユーザーデータテーブル(user_data)の全データ
     */
    List<UserData> findAll();

    /**
     * SQL Serverでユーザーデータテーブル(user_data)の全データを削除する
     */
    void truncateUserData();
}