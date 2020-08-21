package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.thymeleaf.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;

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
    @GetMapping("/")
    public String index(){
        return "index";
    }

    /**
     * ユーザーデータを全件取得する
     * @return ユーザーデータリスト(JSON形式)
     */
    //JSON文字列を返却するために、@ResponseBodyアノテーションを付与
    @GetMapping("/search")
    @ResponseBody
    public String search(){
        // ユーザーデータをIDの昇順に取得し、取得できなければそのまま返す
        List<UserData> userDataList = repository.findAll(new Sort(ASC, "id"));
        // ユーザーデータが取得できなかった場合は、null値を返す
        if(userDataList == null || userDataList.size() == 0){
            return null;
        }
        for(UserData userData : userDataList){
            // 性別を表示用(男,女)に変換
            userData.setSex("1".equals(userData.getSex()) ? "男" : "女");
            // 名前・メモ・性別をエンコード
            userData.setName(encode(userData.getName()));
            userData.setMemo(encode(userData.getMemo()));
            userData.setSex(encode(userData.getSex()));
        }
        // 取得したユーザーデータをJSON文字列に変換し返却
        return getJson(userDataList);
    }

    /**
     * 引数の文字列をエンコードする
     * @param data 任意の文字列
     * @return エンコード後の文字列
     */
    private String encode(String data){
        if(StringUtils.isEmpty(data)){
            return data;
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
     * 引数のUserDataオブジェクトをJSON文字列に変換する
     * @param userDataList UserDataオブジェクトのリスト
     * @return 変換後JSON文字列
     */
    private String getJson(List<UserData> userDataList){
        String retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            retVal = objectMapper.writeValueAsString(userDataList);
        } catch (JsonProcessingException e) {
            System.err.println(e);
        }
        return retVal;
    }
}
