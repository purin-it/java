package com.example.demo;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * プルダウンリストのクラス
 * 「@Scope」アノテーションにより、プルダウンリストを
 * セッション（sessionスコープのBean）としてもたせている
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class DemoPulldown {

    /** 生年月日_月のMapオブジェクト */
    private Map<String, String> monthMap;

    /** 生年月日_日のMapオブジェクト */
    private Map<String, String> dayMap;

    /** 性別のMapオブジェクト */
    private Map<String, String> sexMap;

}
