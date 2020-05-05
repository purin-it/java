package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@Controller
public class DemoController {

    /**
     * RestTemplateオブジェクト
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * ユーザーデータを取得し、初期表示画面に遷移する
     * @param model Modelオブジェクト
     * @return 初期表示画面へのパス
     */
    @RequestMapping("/")
    public String index(Model model){
        // ユーザーデータリストをAPIで取得する
        ResponseEntity<List<UserData>> response = restTemplate.exchange(
                "http://localhost:8085/getUserDataList", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<UserData>>() {});
        List<UserData> userDataList = response.getBody();
        // 各ユーザーデータを編集し、Modelに設定する
        for(UserData userData : userDataList){
            // 名前・メモ・性別をデコード
            userData.setName(decode(userData.getName()));
            userData.setMemo(decode(userData.getMemo()));
            userData.setSex(decode(userData.getSex()));
        }
        model.addAttribute("userDataList", userDataList);
        return "index";
    }

    /**
     * 引数の文字列をデコードする
     * @param data 任意の文字列
     * @return エンコード後の文字列
     */
    private String decode(String data){
        if(StringUtils.isEmpty(data)){
            return "";
        }
        String retVal = null;
        try{
            retVal = URLDecoder.decode(data, "UTF-8");
        }catch (UnsupportedEncodingException e) {
            System.err.println(e);
        }
        return retVal;
    }
}
