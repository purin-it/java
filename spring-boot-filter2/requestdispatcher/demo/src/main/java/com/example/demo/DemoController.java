package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DemoController {

    /**
     * 初期表示を行う
     * @return 初期表示画面のパス
     */
    @RequestMapping("/")
    public String index(){
        return "index";
    }

    /**
     * フィルタ内でエラーが発生した場合の画面遷移を行う
     * @param exception パラメータとして渡された例外
     * @param mav ModelAndViewオブジェクト
     * @return ModelAndViewオブジェクト
     */
    @RequestMapping("/error_filter")
    public ModelAndView errorFilter(@RequestParam("exception") String exception
            , ModelAndView mav){
        //resources/templates下のerror.htmlに遷移
        mav.setViewName("error");
        //error.htmlに埋め込むパラメータ「errClass」に、
        //パラメータとして渡された例外を埋め込む
        mav.addObject("errClass", exception);
        return mav;
    }
}
