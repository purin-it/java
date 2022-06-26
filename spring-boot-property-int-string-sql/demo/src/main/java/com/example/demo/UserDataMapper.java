package com.example.demo;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDataMapper {

    /**
     * SQL Serverで、生年月日が1990年6月20日のpropNum日前以降、
     * 1990年6月20日のpropNum日後以前であるユーザーデータテーブル(user_data)の
     * データを取得する
     * @param propNum プロパティから取得した数値(String型)
     * @return ユーザーデータテーブル(user_data)の指定したIDリストのデータ
     */
    List<UserData> findByBirthdayPropStr(String propNum);

}
