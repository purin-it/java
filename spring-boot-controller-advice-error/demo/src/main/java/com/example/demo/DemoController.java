package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
public class DemoController {

    /**
     * 初期表示画面に遷移する
     * @return 初期表示画面へのパス
     */
    @GetMapping("/")
    public String index(){
        return "index";
    }

    /**
     * NullPointerExceptionを発生させる
     * @return 初期表示画面へのパス
     */
    @PostMapping("/occurNullPointerException")
    public String occurNullPointerException(){
        throw new NullPointerException("NullPointerExceptionが発生しました");
    }

    /**
     * FileNotFoundExceptionを発生させる
     * @return 初期表示画面へのパス
     * @throws FileNotFoundException
     */
    @PostMapping("/occurFileNotFoundException")
    public String occurFileNotFoundException() throws FileNotFoundException{
        throw new FileNotFoundException("FileNotFoundExceptionが発生しました");
    }

    /**
     * IOExceptionを発生させる
     * @return 初期表示画面へのパス
     * @throws Exception
     */
    @PostMapping("/occurOtherException")
    public String occurOtherException() throws IOException{
        throw new IOException("IOExceptionが発生しました");
    }
}
