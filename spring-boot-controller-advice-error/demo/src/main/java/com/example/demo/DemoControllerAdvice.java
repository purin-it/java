package com.example.demo;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.io.FileNotFoundException;

@ControllerAdvice
public class DemoControllerAdvice {

    /**
     * NullPointerExceptionが発生した場合のエラー処理を行う
     * @param exception 発生した例外
     * @return NullPointerExceptionが発生した場合のエラー画面
     */
    @ExceptionHandler(NullPointerException.class)
    public String nullError(NullPointerException exception, Model model) {
        model.addAttribute("errMsg", exception.getMessage());
        return "error_null";
    }

    /**
     * FileNotFoundExceptionが発生した場合のエラー処理を行う
     * @param exception 発生した例外
     * @return FileNotFoundExceptionが発生した場合のエラー画面
     */
    @ExceptionHandler(FileNotFoundException.class)
    public String noFileError(FileNotFoundException exception, Model model) {
        model.addAttribute("errMsg", exception.getMessage());
        return "error_no_file";
    }

    /**
     * 上記以外の例外が発生した場合のエラー処理を行う
     * @param exception 発生した例外
     * @return デフォルトのエラー画面
     */
    @ExceptionHandler(Exception.class)
    public String occurOtherException(Exception exception){
        return "error";
    }
}
