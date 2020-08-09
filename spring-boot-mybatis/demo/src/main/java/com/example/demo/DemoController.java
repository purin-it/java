package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@SessionAttributes(types = {DemoForm.class})
public class DemoController {

    /**
     * ユーザーデータテーブル(user_data)へアクセスするマッパー
     */
    @Autowired
    private UserDataMapper mapper;

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
        Collection<UserData> userDataList = mapper.findAll();
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
    @GetMapping("/update")
    public String update(@RequestParam("id") String id, Model model){
        UserData userData = mapper.findById(Long.parseLong(id));
        model.addAttribute("demoForm", getDemoForm(userData));
        return "input";
    }

    /**
     * 削除確認画面に遷移する
     * @param id 更新対象のID
     * @param model Modelオブジェクト
     * @return 削除確認画面へのパス
     */
    @GetMapping("/delete_confirm")
    public String delete_confirm(@RequestParam("id") String id, Model model){
        UserData userData = mapper.findById(Long.parseLong(id));
        model.addAttribute("demoForm", getDemoForm(userData));
        return "confirm_delete";
    }

    /**
     * 削除処理を行う
     * @param demoForm Formオブジェクト
     * @param model Modelオブジェクト
     * @return 一覧画面の表示処理
     */
    @PostMapping(value = "/delete", params = "next")
    @Transactional(readOnly = false)
    public String delete(DemoForm demoForm, Model model){
        mapper.deleteById(Long.parseLong(demoForm.getId()));
        return "redirect:/to_index";
    }

    /**
     * 削除完了後に一覧画面に戻る
     * @param model Modelオブジェクト
     * @return 一覧画面
     */
    @GetMapping("/to_index")
    public String toIndex(Model model){
        return index(model);
    }

    /**
     * 削除確認画面から一覧画面に戻る
     * @param model Modelオブジェクト
     * @return 一覧画面
     */
    @PostMapping(value = "/delete", params = "back")
    public String confirmDeleteBack(Model model){
        return index(model);
    }

    /**
     * 追加処理を行う画面に遷移する
     * @param model Modelオブジェクト
     * @return 入力・更新画面へのパス
     */
    @PostMapping("/add")
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
    @PostMapping(value = "/confirm", params = "next")
    public String confirm(@Validated DemoForm demoForm, BindingResult result){
        //formオブジェクトのチェック処理を行う
        if(result.hasErrors()){
            //エラーがある場合は、入力画面のままとする
            return "input";
        }
        //アノテーション以外のチェック処理を行い、画面遷移する
        return checkOthers(demoForm, result, "confirm");
    }

    /**
     * 一覧画面に戻る
     * @param model Modelオブジェクト
     * @return 一覧画面の表示処理
     */
    @PostMapping(value = "/confirm", params = "back")
    public String confirmBack(Model model){
        return index(model);
    }

    /**
     * 完了画面に遷移する
     * @param demoForm Formオブジェクト
     * @return 完了画面
     */
    @PostMapping(value = "/send", params = "next")
    @Transactional(readOnly = false)
    public String send(@Validated DemoForm demoForm, BindingResult result){
        //formオブジェクトのチェック処理を行う
        if(result.hasErrors()){
            //エラーがある場合は、入力画面のままとする
            return "input";
        }
        //アノテーション以外のチェック処理を行い、
        //エラーがなければ、更新・追加処理を行う
        String normalPath = "redirect:/complete";
        String checkOthersPath = checkOthers(demoForm, result, normalPath);
        if(normalPath.equals(checkOthersPath)){
            //更新・追加処理を行うエンティティを生成
            UserData userData = getUserData(demoForm);
            //追加・更新処理
            if(demoForm.getId() == null){
                userData.setId(mapper.findMaxId() + 1);
                mapper.create(userData);
            }else{
                mapper.update(userData);
            }
        }
        return checkOthersPath;
    }

    /**
     * 完了画面に遷移する
     * @return 完了画面
     */
    @GetMapping("/complete")
    public String complete(SessionStatus sessionStatus){
        //セッションオブジェクトを破棄
        sessionStatus.setComplete();
        return "complete";
    }

    /**
     * 入力画面に戻る
     * @return 入力画面
     */
    @PostMapping(value = "/send", params = "back")
    public String sendBack(){
        return "input";
    }

    /**
     * アノテーション以外のチェック処理を行い、画面遷移先を返却
     * @param demoForm Formオブジェクト
     * @param result バインド結果
     * @param normalPath 正常時の画面遷移先
     * @return 確認画面または入力画面へのパス
     */
    private String checkOthers(DemoForm demoForm, BindingResult result, String normalPath){
        //** アノテーション以外のチェック処理を行う
        //** エラーがある場合は、エラーメッセージ・(エラー時に赤反転するための)
        //** エラーフィールドの設定を行い、入力画面のままとする
        //生年月日のチェック処理
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
                //性別が不正に書き換えられていないかチェックする
                if(!demoForm.getSexItems().keySet().contains(demoForm.getSex())){
                    result.rejectValue("sex", "validation.sex-invalidate");
                    return "input";
                }
                //エラーチェックに問題が無いので、正常時の画面遷移先に遷移
                return normalPath;
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
        demoForm.setSex_value(userData.getSex_value());
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
        userData.setSex_value(demoForm.getSex_value());
        return userData;
    }

}