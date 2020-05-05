package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileNotFoundException;

@Controller
public class DemoController {

    @RequestMapping("/")
    public String index(Model model){
        String textString;
        try{
            textString = DemoUtil.getTextString();
        }catch (Exception e){
            String errMsg;
            if(e instanceof FileNotFoundException){
                errMsg = "ファイルが見つかりませんでした";
            }else{
                errMsg = "入出力例外が発生しました";
            }
            //エラー時はerror.htmlに遷移
            model.addAttribute("errMsg", errMsg);
            return "error";
        }
        //正常時はindex.htmlに遷移
        model.addAttribute("textString", textString);
        return "index";
    }
}
