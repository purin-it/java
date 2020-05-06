package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
public class DemoController {

    /**
     * ユーザーデータテーブル(user_data)へアクセスするリポジトリ
     */
    @Autowired
    private UserDataRepository repository;

    /**
     * ユーザーデータテーブル(user_data)の全データを取得して返却する
     * @return ユーザーデータリスト
     */
    @ModelAttribute("userDataList")
    public List<UserData> userDataList(){
        return repository.findAll();
    }

    /**
     * 初期表示画面に遷移する
     * @return 初期表示画面へのパス
     */
    @RequestMapping("/")
    public String index(){
        return "index";
    }

}
