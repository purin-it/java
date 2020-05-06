package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes(types = {DemoForm.class})
public class DemoController {

    /**
     * ユーザーデータテーブル(user_data)へアクセスするリポジトリ
     */
    @Autowired
    private UserDataRepository repository;

    /**
     * ユーザーデータテーブル(user_data)のデータを取得して返却する
     * @return ユーザーデータリスト
     */
    @ModelAttribute("demoFormList")
    public List<DemoForm> userDataList(){
        List<DemoForm> demoFormList = new ArrayList<>();
        return demoFormList;
    }

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
     * 初期表示(一覧)画面に遷移する
     * @param model Modelオブジェクト
     * @return 一覧画面へのパス
     */
    @RequestMapping("/")
    public String index(Model model){
        List<DemoForm> demoFormList = new ArrayList<>();
        //ユーザーデータテーブル(user_data)から全データを取得する
        List<UserData> userDataList = repository.findAll(
                new Sort(Sort.Direction.ASC,"id"));
        for(UserData userData : userDataList){
            demoFormList.add(getDemoForm(userData));
        }
        //ユーザーデータリストを更新
        model.addAttribute("demoFormList", demoFormList);
        return "list";
    }

    /**
     * 更新処理を行う画面に遷移する
     * @param id 更新対象のID
     * @param model Modelオブジェクト
     * @return 入力・更新画面へのパス
     */
    @RequestMapping("/update")
    public String update(@RequestParam("id") String id, Model model){
        UserData userData = repository.findById(Long.parseLong(id));
        model.addAttribute("demoForm", getDemoForm(userData));
        return "input";
    }

    /**
     * 削除確認画面に遷移する
     * @param id 更新対象のID
     * @param model Modelオブジェクト
     * @return 削除確認画面へのパス
     */
    @RequestMapping("/delete_confirm")
    public String delete_confirm(@RequestParam("id") String id, Model model){
        UserData userData = repository.findById(Long.parseLong(id));
        model.addAttribute("demoForm", getDemoForm(userData));
        return "confirm_delete";
    }

    /**
     * 削除処理を行う
     * @param demoForm Formオブジェクト
     * @param model Modelオブジェクト
     * @return 一覧画面の表示処理
     */
    @RequestMapping(value = "/delete", params = "next")
    @Transactional(readOnly = false)
    public String delete(DemoForm demoForm, Model model){
        UserData userData = getUserData(demoForm);
        repository.delete(userData);
        return index(model);
    }

    /**
     * 削除確認画面から一覧画面に戻る
     * @param model Modelオブジェクト
     * @return 一覧画面
     */
    @RequestMapping(value = "/delete", params = "back")
    public String confirmDeleteBack(Model model){
        return index(model);
    }

    /**
     * 追加処理を行う画面に遷移する
     * @param model Modelオブジェクト
     * @return 入力・更新画面へのパス
     */
    @RequestMapping("/add")
    public String add(Model model){
        model.addAttribute("demoForm", new DemoForm());
        return "input";
    }

    /**
     * エラーチェックを行い、エラーが無ければ確認画面に遷移し、
     * エラーがあれば入力画面のままとする
     * @param demoForm Formオブジェクト
     * @param result バインド結果
     * @return 確認画面または入力画面へのパス
     */
    @RequestMapping(value = "/confirm", params = "next")
    public String confirm(@Valid DemoForm demoForm, BindingResult result){
        //formオブジェクトのチェック処理を行う
        if(result.hasErrors()){
            //エラーがある場合は、入力画面のままとする
            return "input";
        }
        //生年月日の日付チェック処理を行い、画面遷移する
        return checkDate(demoForm, result);
    }

    /**
     * 一覧画面に戻る
     * @param model Modelオブジェクト
     * @return 一覧画面の表示処理
     */
    @RequestMapping(value = "/confirm", params = "back")
    public String confirmBack(Model model){
        return index(model);
    }

    /**
     * 完了画面に遷移する
     * @param demoForm Formオブジェクト
     * @return 完了画面
     */
    @RequestMapping(value = "/send", params = "next")
    @Transactional(readOnly = false)
    public String send(DemoForm demoForm){
        //更新・追加処理を行うエンティティを生成
        UserData userData = getUserData(demoForm);
        //更新・追加処理
        repository.saveAndFlush(userData);
        return "complete";
    }

    /**
     * 入力画面に戻る
     * @return 入力画面
     */
    @RequestMapping(value = "/send", params = "back")
    public String sendBack(){
        return "input";
    }

    /**
     * 生年月日の日付チェック処理を行い、画面遷移先を返却
     * @param demoForm Formオブジェクト
     * @param result バインド結果
     * @return 確認画面または入力画面へのパス
     */
    private String checkDate(DemoForm demoForm, BindingResult result){
        //生年月日の日付チェック処理を行う
        //エラーがある場合は、エラーメッセージ・エラーフィールドの設定を行い、
        //入力画面のままとする
        int checkDate = DateCheckUtil.checkDate(demoForm.getBirthYear()
                , demoForm.getBirthMonth(), demoForm.getBirthDay());
        switch(checkDate){
            case 1:
                //生年月日_年が空文字の場合のエラー処理
                result.rejectValue("birthYear", "validation.date-empty"
                        , new String[]{"生年月日_年"}, "");
                return "input";
            case 2:
                //生年月日_月が空文字の場合のエラー処理
                result.rejectValue("birthMonth", "validation.date-empty"
                        , new String[]{"生年月日_月"}, "");
                return "input";
            case 3:
                //生年月日_日が空文字の場合のエラー処理
                result.rejectValue("birthDay", "validation.date-empty"
                        , new String[]{"生年月日_日"}, "");
                return "input";
            case 4:
                //生年月日の日付が不正な場合のエラー処理
                result.rejectValue("birthYear", "validation.date-invalidate");
                //生年月日_月・生年月日_日は、エラーフィールドの設定を行い、
                //メッセージを空文字に設定している
                result.rejectValue("birthMonth", "validation.empty-msg");
                result.rejectValue("birthDay", "validation.empty-msg");
                return "input";
            case 5:
                //生年月日の日付が未来日の場合のエラー処理
                result.rejectValue("birthYear", "validation.date-future");
                //生年月日_月・生年月日_日は、エラーフィールドの設定を行い、
                //メッセージを空文字に設定している
                result.rejectValue("birthMonth", "validation.empty-msg");
                result.rejectValue("birthDay", "validation.empty-msg");
                return "input";
            default:
                //formオブジェクト・生年月日の日付のチェック処理を行い、
                //問題なければ確認画面に遷移
                return "confirm";
        }
    }

    /**
     * DemoFormオブジェクトに引数のユーザーデータの各値を設定する
     * @param userData ユーザーデータ
     * @return DemoFormオブジェクト
     */
    private DemoForm getDemoForm(UserData userData){
        DemoForm demoForm = new DemoForm();
        demoForm.setId(String.valueOf(userData.getId()));
        demoForm.setName(userData.getName());
        demoForm.setBirthYear(String.valueOf(userData.getBirthY()));
        demoForm.setBirthMonth(String.valueOf(userData.getBirthM()));
        demoForm.setBirthDay(String.valueOf(userData.getBirthD()));
        demoForm.setSex(userData.getSex());
        return demoForm;
    }

    /**
     * UserDataオブジェクトに引数のフォームの各値を設定する
     * @param demoForm DemoFormオブジェクト
     * @return ユーザーデータ
     */
    private UserData getUserData(DemoForm demoForm){
        UserData userData = new UserData();
        if(!DateCheckUtil.isEmpty(demoForm.getId())){
            userData.setId(Long.valueOf(demoForm.getId()));
        }
        userData.setName(demoForm.getName());
        userData.setBirthY(Integer.valueOf(demoForm.getBirthYear()));
        userData.setBirthM(Integer.valueOf(demoForm.getBirthMonth()));
        userData.setBirthD(Integer.valueOf(demoForm.getBirthDay()));
        userData.setSex(demoForm.getSex());
        return userData;
    }

}