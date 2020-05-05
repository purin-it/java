package com.example.demo.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Random;

public class RequestTokenInterceptor implements HandlerInterceptor {

    /**
     * エラー画面へのパス
     */
    private static final String ERROR_PATH = "/error";

    /**
     * 乱数生成クラスのインスタンス
     */
    @Autowired
    private Random random;

    //コントローラクラスの画面遷移処理が呼ばれる前に実行されるメソッド
    @Override
    public boolean preHandle(HttpServletRequest request
            , HttpServletResponse response, Object handler) throws Exception {
        // エラー画面に遷移する場合以外は、リクエストトークンのチェックを行う
        if (!ERROR_PATH.equals(request.getRequestURI())) {
            // セッションに格納したリクエストトークンを取得
            String sesReqToken = null;
            HttpSession session = request.getSession(false);
            if (session != null) {
                sesReqToken = (String) session.getAttribute("sesReqToken");
            }
            // リクエストパラメータからのリクエストトークンを取得
            String reqToken = request.getParameter("reqToken");
            // リクエストトークンが一致しない場合、エラー画面に遷移
            if (!isEqualString(sesReqToken, reqToken)) {
                response.sendRedirect("/error");
                return false;
            }
            return true;
        }
        return true;
    }

    //コントローラクラスの画面遷移処理が呼ばれた後に実行されるメソッド
    @Override
    public void postHandle(HttpServletRequest request
            , HttpServletResponse response, Object handler
            , @Nullable ModelAndView modelAndView) {
        // リクエストトークンを生成し設定
        String reqToken = String.valueOf(random.nextInt(100000000));
        modelAndView.addObject("reqToken", reqToken);
        //生成したリクエストトークンをセッションに格納
        HttpSession session = request.getSession(false);
        if (session == null) {
            session = request.getSession(true);
        }
        session.setAttribute("sesReqToken", reqToken);
    }

    /**
     * 引数の文字列が一致するかどうか返却する
     * @param str1 比較対象文字列1
     * @param str2 比較対象文字列2
     * @return 比較結果
     */
    private boolean isEqualString(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return true;
        } else if (str1 == null && str2 != null) {
            return false;
        }
        return str1.equals(str2);
    }
}
