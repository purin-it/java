package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemoController {

    /**
     * ポート番号取得処理
     */
    @Autowired
    private DemoGetPortNum demoComponent;

    /**
     * データベース接続情報取得処理
     */
    @Autowired
    private DemoGetDataSource demoGetDataSource;

    @RequestMapping("/")
    public String index(Model model){
        //ポート番号を取得し、modelオブジェクトに設定
        String portNum = demoComponent.getPortNum();
        model.addAttribute("portNum", portNum);

        //データベース接続情報を取得し、modelオブジェクトに設定
        String dataSource = demoGetDataSource.getDataSource();
        model.addAttribute("dataSource", dataSource);
        return "index";
    }
}
