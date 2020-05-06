package com.example.demo;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * RuntimeException発生時の処理を実装
 * 通常、SpringBootアプリケーションでエラーが発生した場合は resources/templates/error.html に
 * 遷移するようになっているが、その遷移先を変えたり、文言追加オブジェクト等を追加するには、
 * このクラスのように、ErrorControllerクラスをimplementsしたクラスを作成する
 */
@Controller
public class DemoExceptionController implements ErrorController {

    /**
     * エラーが発生した場合の画面遷移
     * @param e RuntimeException例外
     * @param mav ModelAndViewオブジェクト
     * @return ModelAndViewオブジェクト
     */
    @RequestMapping("/error")
    @ExceptionHandler(RuntimeException.class)
    public ModelAndView runtimeExceptionHandler(RuntimeException e, ModelAndView mav){
        mav.setViewName("error_filter");
        mav.addObject("errMsg", "システムエラーが発生しました、ログを確認してください。");
        return mav;
    }

    /**
     * エラーパスを取得
     * (このメソッドを追加しないとコンパイルエラーになるため追加)
     * @return エラーパス
     */
    @Override
    public String getErrorPath() {
        return "";
    }
}