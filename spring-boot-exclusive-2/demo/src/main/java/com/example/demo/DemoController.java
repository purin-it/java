package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
     * Demoサービスクラスへのアクセス
     */
    @Autowired
    private DemoService demoService;

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
        //ユーザーデータリストを取得
        List<DemoForm> demoFormList = demoService.demoFormList();
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
        //更新対象のユーザーデータを取得
        DemoForm demoForm = demoService.findById(id);
        //ユーザーデータを更新
        model.addAttribute("demoForm", demoForm);
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
        //削除対象のユーザーデータを取得
        DemoForm demoForm = demoService.findById(id);
        //ユーザーデータを更新
        model.addAttribute("demoForm", demoForm);
        return "confirm_delete";
    }

    /**
     * 削除処理を行う
     * @param demoForm Formオブジェクト
     * @param model Modelオブジェクト
     * @return 一覧画面の表示処理
     */
    @RequestMapping(value = "/delete", params = "next")
    public String delete(DemoForm demoForm, Model model){
        //指定したユーザーデータを削除
        demoService.deleteById(demoForm.getId());
        //一覧画面に遷移
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
        //生年月日の日付チェック処理を行い、画面遷移する
        return demoService.checkForm(demoForm, result);
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
     * データ更新処理
     * @param demoForm Formオブジェクト
     * @return 完了画面
     */
    @RequestMapping(value = "/send", params = "next")
    public String send(DemoForm demoForm, Model model){
        try{
            //ユーザーデータがあれば更新し、無ければ追加する
            demoService.createOrUpdate(demoForm);
        }catch(RuntimeException ex){
            //エラー時は、システムエラー画面に遷移
            if(ex.toString().contains("ORA-30006")){
                model.addAttribute("errClass", "WAITタイムアウトエラー");
            }else{
                model.addAttribute("errClass", ex.toString());
            }
            return "system_error";
        }
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

}
