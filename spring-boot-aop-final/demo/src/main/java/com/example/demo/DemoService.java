package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    /**
     * ユーザーデータテーブル(user_data)へアクセスするリポジトリ
     */
    @Autowired
    private UserDataRepository repository;

    /**
     * ユーザーデータを取得し、そのJSON文字列を返す
     * @return ユーザーデータ(JSON)
     */
    public String getUserData(){
        UserData userData = repository.findUserDataById(1L);
        return getJson(userData);
    }

    /**
     * finalメソッドにより、ユーザーデータを取得し、そのJSON文字列を返す
     * @return ユーザーデータ(JSON)
     */
    public final String getUserDataByFinal(){
        UserData userData = repository.findUserDataById(1L);
        return getJson(userData);
    }

    /**
     * 引数のUserDataオブジェクトをJSON文字列に変換する
     * @param userData UserDataオブジェクト
     * @return 変換後JSON文字列
     */
    private String getJson(UserData userData){
        String retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            retVal = objectMapper.writeValueAsString(userData);
        } catch (JsonProcessingException e) {
            System.err.println(e);
        }
        return retVal;
    }
}
