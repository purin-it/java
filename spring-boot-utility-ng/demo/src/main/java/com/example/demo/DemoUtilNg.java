package com.example.demo;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

// セッションから指定したキー値に対応するリストを取得するユーティリティクラス(NG例)
public class DemoUtilNg {

    /*
       ★NG(1): 不必要なインスタンス変数
       ローカル変数にできる変数はローカル変数にした方がよい
     */
    private ServletRequestAttributes requestAttributes = null;
    private HttpSession session = null;
    private HashMap<String, List<String>> hashMap = null;

    /**
     * セッションから指定したキー値に対応するリストを取得する
     * @param key キー値
     * @return 指定したキー値に対応するリスト
     * @throws IllegalStateException 不適切な状態でのメソッド呼出例外
     * @throws IllegalArgumentException 不適切な引数指定例外
     */
    /*
      ★NG(2): staticメソッドにしていない
      staticメソッドでないと、呼出側でこのクラスをnewしてからこのメソッドを呼び出す必要がある
    */
    public List<String> getHashList(String key)
            throws IllegalStateException, IllegalArgumentException{
        /*
          ★NG(3): nullを返せば済むところをわざわざ例外をスローしている
          例外を発生させると、呼出側で例外処理を行わなければならず手間がかかる
         */
        // リクエストオブジェクトがnullの場合は、IllegalStateException例外をスローする
        requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(requestAttributes == null){
            throw new IllegalStateException("リクエストオブジェクトが取得できませんでした");
        }
        // セッションがnullの場合は、IllegalStateException例外をスローする
        session = requestAttributes.getRequest().getSession(false);
        if(session == null){
            throw new IllegalStateException("セッションが取得できませんでした");
        }
        // セッションからHashMapが取得できない場合は、IllegalStateException例外をスローする
        hashMap = (HashMap<String, List<String>>)session.getAttribute("sesHashMap");
        if(hashMap == null){
            throw new IllegalStateException("セッションからHashMapが取得できませんでした");
        }
        /*
           ★NG(4): 不必要な引数のチェック処理
           hashMap.get(null)やhashMap.get("")としてもエラーにならずnullを返却するだけなので、
           例外処理はしない方が処理を簡略にできる
         */
        // 引数がnullまたは空の場合は、IllegalArgumentException例外をスローする
        if(StringUtils.isEmpty(key)){
            throw new IllegalArgumentException("引数がnullまたは空です");
        }
        // HashMapから引数で指定されたキーを取得する
        return hashMap.get(key);
    }
}
