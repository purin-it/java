package com.example.demo;

import org.apache.ibatis.annotations.Mapper;
import java.util.ArrayList;

@Mapper
public interface UserDataMapper {

    /**
     * 指定したIDリストをもつユーザーデータテーブル(user_data)のデータを取得する
     * @param idList IDリスト
     * @return ユーザーデータテーブル(user_data)の指定したIDリストのデータ
     */
    ArrayList<UserData> findByIdList(ArrayList<Long> idList);
}