package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DemoController {

    @RequestMapping("/")
    public ModelAndView index(ModelAndView mav){
        String str = DemoUtil.getMessage();
        mav.addObject("msg", str);
        mav.setViewName("index");
        return mav;
    }

}