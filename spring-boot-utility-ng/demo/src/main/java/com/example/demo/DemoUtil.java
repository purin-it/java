package com.example.demo;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

// セッションから指定したキー値に対応するリストを取得するユーティリティクラス
public class DemoUtil {

    /**
     * セッションから指定したキー値に対応するリストを取得する
     * @param key キー値
     * @return 指定したキー値に対応するリスト
     */
    public static List<String> getHashList(String key){
        // リクエストオブジェクトがnullの場合は、nullを返却する
        ServletRequestAttributes requestAttributes
                = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(requestAttributes == null){
            return null;
        }
        // セッションがnullの場合は、nullを返却する
        HttpSession session = requestAttributes.getRequest().getSession(false);
        if(session == null){
            return null;
        }
        // セッションからHashMapが取得できない場合は、nullを返却する
        HashMap<String, List<String>> hashMap
                = (HashMap<String, List<String>>)session.getAttribute("sesHashMap");
        if(hashMap == null){
            return null;
        }
        // HashMapから引数で指定されたキーを取得する
        return hashMap.get(key);
    }
}
