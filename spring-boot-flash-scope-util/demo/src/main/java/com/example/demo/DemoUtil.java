package com.example.demo;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class DemoUtil {

    /**
     * リダイレクト先に渡すFormオブジェクトのキー
     */
    private static final String REDIRECT_FORM_KEY
            = DemoUtil.class.getName().concat(".REDIRECT_FORM");

    /**
     * リダイレクト先に渡したいFormオブジェクトをFlash Scopeに格納する
     * @param demoForm Formオブジェクト
     */
    public static void setRedirectForm(DemoForm demoForm){
        // 引数がnullの場合はIllegalArgumentExceptionをスロー
        if(demoForm == null){
            throw new IllegalArgumentException("demoForm is null");
        }
        // リクエストが取得できない場合はRuntimeExceptionをスロー
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();
        if(request == null){
            throw new RuntimeException("request is null");
        }
        // リダイレクト先に渡したいFormオブジェクトをFlash Scopeに格納
        FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
        flashMap.put(REDIRECT_FORM_KEY, demoForm);
    }

    /**
     * リダイレクト先に渡したいFormオブジェクトをFlash Scopeから取得する
     * @return Formオブジェクト
     */
    public static DemoForm getRedirectForm(){
        // リクエストが取得できない場合はRuntimeExceptionをスロー
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();
        if(request == null){
            throw new RuntimeException("request is null");
        }
        // Flash ScopeからFormオブジェクトを取得
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if(flashMap != null){
            return (DemoForm) flashMap.get(REDIRECT_FORM_KEY);
        }
        return null;
    }
}
