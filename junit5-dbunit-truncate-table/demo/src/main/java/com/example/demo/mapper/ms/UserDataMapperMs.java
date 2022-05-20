package com.example.demo.mapper.ms;

import com.example.demo.mapper.UserData;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface UserDataMapperMs {

    /**
     * MySQLでユーザーデータテーブル(user_data)の全データを取得する
     * @return ユーザーデータテーブル(user_data)の全データ
     */
    List<UserData> findAll();

    /**
     * MySQLでユーザーデータテーブル(user_data)の全データを削除する
     */
    void truncateUserData();
}