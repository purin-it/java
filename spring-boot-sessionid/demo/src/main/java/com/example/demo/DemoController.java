package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class DemoController {

    /**
     * 初期表示画面に遷移する
     * @param request HttpServletリクエスト
     * @param model Modelオブジェクト
     * @return 初期表示画面
     */
    @GetMapping("/")
    public String index(HttpServletRequest request, Model model){
        // セッションIDと、Cookieに設定されたセッションIDを設定する
        HttpSession session = request.getSession(false);
        model.addAttribute("sessionId", session == null ? "sessionは未作成" : session.getId());
        model.addAttribute("cookieSessionId", getCookieSessionId(request));
        return "index";
    }

    /**
     * セッションを生成し同一画面に遷移する
     * @param request HttpServletリクエスト
     * @param model Modelオブジェクト
     * @return 初期表示画面
     */
    @PostMapping("/addSession")
    public String addSession(HttpServletRequest request, Model model){
        HttpSession session = request.getSession(true);
        model.addAttribute("sessionId", session == null ? "sessionは未作成" : session.getId());
        model.addAttribute("cookieSessionId", getCookieSessionId(request));
        return "index";
    }

    /**
     * メイン画面に遷移する
     * @param request HttpServletリクエスト
     * @param model Modelオブジェクト
     * @return 初期表示画面
     */
    @PostMapping("/toMain")
    public String toMain(HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);
        model.addAttribute("sessionId", session == null ? "sessionは未作成" : session.getId());
        model.addAttribute("cookieSessionId", getCookieSessionId(request));
        return "main";
    }

    /**
     * 初期表示画面に遷移する
     * @param request HttpServletリクエスト
     * @param model Modelオブジェクト
     * @return 初期表示画面
     */
    @PostMapping("/toIndex")
    public String toIndex(HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);
        model.addAttribute("sessionId", session == null ? "sessionは未作成" : session.getId());
        model.addAttribute("cookieSessionId", getCookieSessionId(request));
        return "index";
    }

    /**
     * 新しいタブを開く
     * @param request HttpServletリクエスト
     * @param model Modelオブジェクト
     * @return 新規タブ画面へのパス
     */
    @GetMapping("/openNewTab")
    public String openNewTab(HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);
        model.addAttribute("sessionId", session == null ? "sessionは未作成" : session.getId());
        model.addAttribute("cookieSessionId", getCookieSessionId(request));
        return "new_tab";
    }

    /**
     * 新しい画面を開く
     * @param request HttpServletリクエスト
     * @param model Modelオブジェクト
     * @return 新規オープン画面へのパス
     */
    @GetMapping("/openNewWindow")
    public String openNewWindow(HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);
        model.addAttribute("sessionId", session == null ? "sessionは未作成" : session.getId());
        model.addAttribute("cookieSessionId", getCookieSessionId(request));
        return "new_window";
    }

    /**
     * CookieのセッションIDを取得する
     * @param request HttpServletリクエスト
     * @return CookieのセッションID
     */
    private String getCookieSessionId(HttpServletRequest request){
        if(request == null || request.getCookies() == null){
            return "CookieのセッションIDは未設定";
        }
        Cookie cookie[] = request.getCookies();
        for (int i = 0 ; i < cookie.length ; i++) {
            if ("JSESSIONID".equals(cookie[i].getName())) {
                return cookie[i].getValue();
            }
        }
        return "CookieのセッションIDは未設定";
    }
}
