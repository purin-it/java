package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemoController {

    @Autowired
    private DemoComponent demoComponent;

    @RequestMapping("/")
    public String index(Model model){
        String textString1;
        String textString2;
        try{
            //2つのファイルからそれぞれ値を取得
            textString1 = DemoUtil.getTextString("C:\\tmp\\test.txt");
            textString2 = demoComponent.getTextString("C:\\tmp\\test2.txt");
        }catch (Exception e){
            String errMsg = "入出力例外が発生しました";
            //エラー時はerror.htmlに遷移
            model.addAttribute("errMsg", errMsg);
            return "error";
        }
        //正常時はindex.htmlに遷移
        model.addAttribute("textString1", textString1);
        model.addAttribute("textString2", textString2);
        return "index";
    }
}
