package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DemoController {

    @RequestMapping("/")
    public ModelAndView index(ModelAndView mav){
        //fromStr文字列をDIを利用しない形で取得
        DemoObject obj = new DemoObject();
        String str = obj.getString();

        //取得したfromStr文字列を画面表示用に設定
        mav.addObject("fromStr", str);

        //画面遷移先(templates/index.html)を設定
        mav.setViewName("index");
        return mav;
    }

}
