package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DemoController {

    @Autowired
    private DemoRepository demoRepository;

    @Autowired
    private DemoService demoService;

    @Autowired
    private DemoSubController demoSubController;

    @RequestMapping("/")
    public ModelAndView index(ModelAndView mav){
        //fromStr文字列をRepository, Service, Controllerの各アノテーションのDIから取得
        String str1 = demoRepository.getString();
        String str2 = demoService.getString();
        String str3 = demoSubController.getString();
        String str = str1 + " : " + str2 + " : " + str3;

        //取得したfromStr文字列を画面表示用に設定
        mav.addObject("fromStr", str);

        //画面遷移先(templates/index.html)を設定
        mav.setViewName("index");
        return mav;
    }

}
