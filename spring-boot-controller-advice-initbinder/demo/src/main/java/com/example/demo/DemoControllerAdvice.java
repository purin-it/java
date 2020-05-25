package com.example.demo;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class DemoControllerAdvice {

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        //文字列の前後の空白を取り除き、サニタイジングする
        dataBinder.registerCustomEditor(
                String.class, new DemoStringConverter(false));
    }

}
