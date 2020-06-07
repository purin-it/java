package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemoController {

    /**
     * Spring-Security用のユーザーアカウント情報を
     * 取得・設定するサービスへのアクセス
     */
    @Autowired
    private UserPassAccountService userDetailsService;

    /**
     * パスワードをBCryptで暗号化するクラスへのアクセス
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * ログイン画面に遷移する
     * @return ログイン画面へのパス
     */
    @GetMapping("/login")
    public String login(){
        //ユーザーパスワードデータテーブル(user_pass)へユーザー情報を登録する。
        //その際、パスワードはBCryptで暗号化する
        userDetailsService.registerUser("user"
                , passwordEncoder.encode("pass"), "USER");
        userDetailsService.registerUser("admin"
                , passwordEncoder.encode("pass"), "ADMIN");
        //ログイン画面に遷移する
        return "login";
    }

    /**
     * 初期表示画面に遷移する
     * @return 初期表示画面へのパス
     */
    @RequestMapping("/")
    public String index(){
        return "index";
    }

    /**
     * 一般ユーザーの画面に遷移する
     * @return 一般ユーザーの画面へのパス
     */
    @RequestMapping("/has_user_auth")
    public String has_user_auth(){
        return "user";
    }

    /**
     * 管理者ユーザーの画面に遷移する
     * @return 管理者ユーザーの画面へのパス
     */
    @RequestMapping("/has_admin_auth")
    public String has_admin_auth(){
        return "admin";
    }

    /**
     * エラー画面に遷移する
     * @return エラー画面へのパス
     */
    @RequestMapping("/toError")
    public String toError(){
        return "error";
    }

}
