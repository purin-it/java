package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;

@Controller
public class DemoController {

    @RequestMapping("/")
    public ModelAndView index(ModelAndView mav, HttpServletRequest request){
        //リクエストを送ったクライアントのIPアドレスを取得する
        String ipAddress = request.getRemoteAddr();
        mav.addObject("ipAddress", ipAddress);
        mav.setViewName("index");
        return mav;
    }
}
