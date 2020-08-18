package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Controller
public class DemoController {

    /**
     * ユーザーデータテーブル(user_data)へアクセスするリポジトリ
     */
    @Autowired
    private UserDataRepository repository;

    /**
     * 追加・更新用Formオブジェクトを初期化して返却する
     * @return 追加・更新用Formオブジェクト
     */
    @ModelAttribute("demoForm")
    public DemoForm createDemoForm(){
        DemoForm demoForm = new DemoForm();
        return demoForm;
    }

    /**
     * 初期表示画面に遷移
     * @return 初期表示画面
     */
    @RequestMapping("/")
    public String index(){
        return "index";
    }

    /**
     * Post方式確認用の画面に遷移する
     * @model Modelオブジェクト
     * @return Post方式確認用の画面へのパス
     */
    @GetMapping(path = "/index_post")
    public String index_post(Model model){
        // ユーザーデータリストを取得
        List<UserData> userDataList = getUserDataList();
        model.addAttribute("userDataList", userDataList);
        return "index_post";
    }

    /**
     * Post&Redirect&Get方式確認用の画面に遷移する
     * @model Modelオブジェクト
     * @return Post&Redirect&Get方式確認用の画面へのパス
     */
    @GetMapping(path = "/index_prg")
    public String index_prg(Model model){
        // ユーザーデータリストを取得
        List<UserData> userDataList = getUserDataList();
        model.addAttribute("userDataList", userDataList);
        return "index_prg";
    }

    /**
     * Post方式でデータを登録する
     * @param demoForm 登録用フォーム
     * @param model Modelオブジェクト
     * @return Post方式確認用の画面へのパス
     */
    @PostMapping(path = "/addDataPost")
    public String addDataPost(DemoForm demoForm, Model model){
        // 登録用フォームから送られてきたデータを登録する
        UserData userData = getUserData(demoForm);
        repository.saveAndFlush(userData);
        // Post方式確認用の初期表示画面に遷移する
        return index_post(model);
    }

    /**
     * Post&Redirect&Get方式でデータを登録する
     * @param demoForm 登録用フォーム
     * @param model Modelオブジェクト
     * @return Post方式確認用の画面へのパス
     */
    @PostMapping(path = "/addDataPostRedirectGet")
    public String addDataPostRedirectGet(DemoForm demoForm, Model model){
        // 登録用フォームから送られてきたデータを登録する
        UserData userData = getUserData(demoForm);
        repository.saveAndFlush(userData);
        // ユーザーデータリストを取得
        List<UserData> userDataList = getUserDataList();
        model.addAttribute("userDataList", userDataList);
        // Post&Redirect&Get方式確認用の初期表示画面に遷移する
        return "redirect:/index_prg";
    }

    /**
     * ユーザーリストを取得
     * @return ユーザーリスト
     */
    private List<UserData> getUserDataList(){
        // ユーザーデータをIDの昇順に取得し、取得できなければそのまま返す
        List<UserData> userDataList = repository.findAll(new Sort(ASC, "id"));
        // ユーザーデータが取得できなかった場合は、null値を返す
        if(userDataList == null || userDataList.size() == 0){
            return null;
        }
        for(UserData userData : userDataList){
            // 性別を表示用(男,女)に変換
            userData.setSex("1".equals(userData.getSex()) ? "男" : "女");
        }
        return userDataList;
    }

    /**
     * UserDataオブジェクトに引数のフォームの各値を設定する
     * @param demoForm DemoFormオブジェクト
     * @return ユーザーデータ
     */
    private UserData getUserData(DemoForm demoForm){
        UserData userData = new UserData();
        userData.setId(getMaxId() + 1);
        userData.setName(demoForm.getName());
        userData.setBirthY(Integer.valueOf(demoForm.getBirthYear()));
        userData.setBirthM(Integer.valueOf(demoForm.getBirthMonth()));
        userData.setBirthD(Integer.valueOf(demoForm.getBirthDay()));
        userData.setSex(demoForm.getSex());
        userData.setMemo(demoForm.getMemo());
        return userData;
    }

    /**
     * 登録IDの最大値を取得する
     * @return 登録IDの最大値
     */
    private long getMaxId(){
        List<UserData> userDataList = repository.findAll(new Sort(DESC, "id"));
        if(userDataList == null || userDataList.size() == 0){
            return 0;
        }else{
            return userDataList.get(0).getId();
        }
    }
}
