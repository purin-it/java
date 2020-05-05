package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

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
        // ユーザーデータをAPIで取得する
        UserData userData = restTemplate.getForObject(
                "http://localhost:8085/getUserData", UserData.class);
        if(userData != null){
            // 名前・メモ・性別をデコード
            userData.setName(decode(userData.getName()));
            userData.setMemo(decode(userData.getMemo()));
            userData.setSex(decode(userData.getSex()));
        }else{
            userData = new UserData();
        }
        // ユーザーデータをModelオブジェクトに設定
        model.addAttribute("userData", userData);
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
