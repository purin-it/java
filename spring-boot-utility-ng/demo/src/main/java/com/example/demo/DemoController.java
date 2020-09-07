package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class DemoController {

    /**
     * ハッシュマップをセッションに追加し、初期表示画面に遷移
     * @param request HttpServletRequestオブジェクト
     * @return 初期表示画面へのパス
     */
    @GetMapping("/")
    public String index(HttpServletRequest request){
        // セッションを生成
        HttpSession session = request.getSession(true);

        // ハッシュマップを生成し、セッションに追加
        HashMap<String, List<String>> hashMap = createHashMap();
        session.setAttribute("sesHashMap", hashMap);

        return "index";
    }

    /**
     * セッションから指定したキー値に対応するリストを取得し、次画面に遷移
     * @param model Modelオブジェクト
     * @param request HttpServletRequestオブジェクト
     * @return 次画面へのパス
     */
    @PostMapping("/next")
    public String next(Model model, HttpServletRequest request){
        // セッションから指定したキー値に対応するリストを取得し、sessionListに設定
        List<String> hashList = DemoUtil.getHashList("key1");
        model.addAttribute("sessionList", hashList);

        // セッションから指定したキー値に対応するリストを取得し、sessionListに設定(NG例呼出)
        //try{
        //    List<String> hashList = new DemoUtilNg().getHashList("key1");
        //    model.addAttribute("sessionList", hashList);
        //}catch(Exception ex){
        //    model.addAttribute("sessionList", null);
        //}

        // セッションの値を破棄し、次画面に遷移
        request.getSession(false).invalidate();
        return "next";
    }

    /**
     * セッションに設定するハッシュマップを生成する
     * @return 生成したハッシュマップ
     */
    private HashMap<String, List<String>> createHashMap(){
        HashMap<String, List<String>> hashMap = new HashMap<>();

        List<String> hashList1 = new ArrayList<>();
        hashList1.add("item1");
        hashList1.add("item2");
        hashList1.add("item3");
        hashMap.put("key1", hashList1);

        List<String> hashList2 = new ArrayList<>();
        hashList2.add("item4");
        hashList2.add("item5");
        hashMap.put("key2", hashList2);

        return hashMap;
    }
}
