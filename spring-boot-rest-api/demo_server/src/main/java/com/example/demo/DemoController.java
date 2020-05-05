package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
public class DemoController {

    /**
     * ユーザーデータテーブル(user_data)へアクセスするリポジトリ
     */
    @Autowired
    private UserDataRepository repository;

    /**
     * ユーザーデータを1件取得する
     * @return ユーザーデータ(JSON形式)
     */
    //JSON文字列を返却するために、@ResponseBodyアノテーションを付与
    @RequestMapping("/getUserData")
    @ResponseBody
    private String getUserData(){
        // ユーザーデータを取得し、取得できなければそのまま返す
        UserData userData = repository.findUserDataById(Long.valueOf(1));
        // ユーザーデータが取得できなかった場合は、null値を返す
        if(userData == null){
            return null;
        }
        // 性別を表示用(男,女)に変換
        userData.setSex("1".equals(userData.getSex()) ? "男" : "女");
        // 名前・メモ・性別をエンコード
        userData.setName(encode(userData.getName()));
        userData.setMemo(encode(userData.getMemo()));
        userData.setSex(encode(userData.getSex()));
        // 取得したユーザーデータをJSON文字列に変換し返却
        return getJsonData(userData);
    }

    /**
     * 引数の文字列をエンコードする
     * @param data 任意の文字列
     * @return エンコード後の文字列
     */
    private String encode(String data){
        if(StringUtils.isEmpty(data)){
            return "";
        }
        String retVal = null;
        try{
            retVal = URLEncoder.encode(data, "UTF-8");
        }catch (UnsupportedEncodingException e) {
            System.err.println(e);
        }
        return retVal;
    }

    /**
     * 引数のオブジェクトをJSON文字列に変換する
     * @param data オブジェクトのデータ
     * @return 変換後JSON文字列
     */
    private String getJsonData(Object data){
        String retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            retVal = objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            System.err.println(e);
        }
        return retVal;
    }

}
