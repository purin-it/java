package com.example.demo.base;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemoErrorController implements ErrorController {

    /**
     * エラーが発生した場合の画面遷移
     * @return エラー画面へのパス
     */
    @RequestMapping("/error")
    public String to_error() {
        return "error";
    }

    /**
     * エラーパスを取得
     * (このメソッドを追加しないとコンパイルエラーになるため追加)
     *
     * @return エラーパス
     */
    @Override
    public String getErrorPath() {
        return "";
    }
}
