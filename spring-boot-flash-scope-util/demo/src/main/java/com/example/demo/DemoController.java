package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DemoController {

    /**
     * Formオブジェクトを初期化して返却する
     * @return Formオブジェクト
     */
    @ModelAttribute("demoForm")
    public DemoForm createDemoForm(){
        DemoForm demoForm = new DemoForm();
        return demoForm;
    }

    /**
     * 入力画面に遷移する
     * @return 入力画面へのパス
     */
    @RequestMapping("/")
    public String index(){
        return "input";
    }

    /**
     * 確認画面に遷移するためのリダイレクト遷移を行う
     * @param demoForm demoFormオブジェクト
     * @param mav ModelAndViewオブジェクト
     * @return ModelAndViewオブジェクト
     */
    @RequestMapping("/confirm")
    public ModelAndView confirm(DemoForm demoForm, ModelAndView mav){
        System.out.println("confirmメソッド　demoForm : " + demoForm);
        // リダイレクト先に渡したいFormオブジェクトをFlash Scopeに格納
        DemoUtil.setRedirectForm(demoForm);
        mav.setViewName("redirect:/confirm_redirect");
        return mav;
    }

    /**
     * 確認画面に遷移する
     * @param mav ModelAndViewオブジェクト
     * @return ModelAndViewオブジェクト
     */
    @RequestMapping("/confirm_redirect")
    public ModelAndView confirm_redirect(ModelAndView mav){
        // Flash ScopeからFormオブジェクトを取得し、Formオブジェクトの各値が取得できることを確認
        DemoForm demoForm = DemoUtil.getRedirectForm();
        System.out.println("confirm_redirectメソッド　demoForm : " + demoForm);
        // 画面上でdemoFormの設定値を表示するために、戻り値のModelAndViewオブジェクトに
        // demoFormオブジェクトを設定
        mav.addObject("demoForm", demoForm);
        mav.setViewName("confirm");
        return mav;
    }

    /**
     * 完了画面に遷移する
     * @param mav ModelAndViewオブジェクト
     * @return ModelAndViewオブジェクト
     */
    @RequestMapping("/complete")
    public ModelAndView complete(ModelAndView mav){
        // Flash ScopeからFormオブジェクトを取得し、Formオブジェクトがクリアされていることを確認
        DemoForm demoForm = DemoUtil.getRedirectForm();
        System.out.println("completeメソッド　demoForm : " + demoForm);
        mav.setViewName("complete");
        return mav;
    }
}
