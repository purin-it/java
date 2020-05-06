package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DemoController {

    @Autowired
    private DemoComponent demoComponent;

    @RequestMapping("/")
    public ModelAndView index(ModelAndView mav){
        //fromStr文字列をComponentアノテーションのDIから取得
        String str = demoComponent.getString();

        //取得したfromStr文字列を画面表示用に設定
        mav.addObject("fromStr", str);

        //画面遷移先(templates/index.html)を設定
        mav.setViewName("index");
        return mav;
    }

}
