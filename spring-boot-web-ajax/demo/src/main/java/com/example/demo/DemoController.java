package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;

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
     * 初期表示画面に遷移する
     * @return 初期表示画面へのパス
     */
    @RequestMapping("/")
    public String index(){
        return "index";
    }

    /**
     * 引数の検索IDに対応するDemoFormデータを取得する
     * @param id 検索ID
     * @return DemoFormデータ(JSON形式)
     */
    //JSON文字列を返却するために、@ResponseBodyアノテーションを付与
    @RequestMapping("/search")
    @ResponseBody
    public String search(@RequestParam("id") String id){
        // ユーザーデータを取得し、取得できなければそのまま返す
        UserData userData = repository.findUserDataById(Long.valueOf(id));
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
        return getJson(userData);
    }

    /**
     * 引数の文字列をエンコードする
     * @param data 任意の文字列
     * @return エンコード後の文字列
     */
    private String encode(String data){
        String retVal = null;
        try{
            retVal = URLEncoder.encode(data, "UTF-8");
        }catch (UnsupportedEncodingException e) {
            System.err.println(e);
        }
        return retVal;
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
