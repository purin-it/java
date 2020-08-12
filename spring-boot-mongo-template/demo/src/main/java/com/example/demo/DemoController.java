package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.support.SessionStatus;
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
     * 追加・更新用Formオブジェクトを初期化して返却する
     * @return 追加・更新用Formオブジェクト
     */
    @ModelAttribute("demoForm")
    public DemoForm createDemoForm(){
        DemoForm demoForm = new DemoForm();
        return demoForm;
    }

    /**
     * 初期表示(一覧)画面に遷移する
     * @return 一覧画面へのパス
     */
    @RequestMapping("/")
    public String index(Model model){
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
    @GetMapping("/update")
    public String update(@RequestParam("id") String id, Model model){
        //更新対象のユーザーデータを取得
        DemoForm demoForm = demoService.findByPKeyId(id);
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
    @GetMapping("/delete_confirm")
    public String delete_confirm(@RequestParam("id") String id, Model model){
        //削除対象のユーザーデータを取得
        DemoForm demoForm = demoService.findByPKeyId(id);
        //ユーザーデータを更新
        model.addAttribute("demoForm", demoForm);
        return "confirm_delete";
    }

    /**
     * 削除処理を行う
     * @param demoForm 追加・更新用Formオブジェクト
     * @return 一覧画面の表示処理
     */
    @PostMapping(value = "/delete", params = "next")
    public String delete(DemoForm demoForm){
        //指定したユーザーデータを削除
        demoService.delete(demoForm);
        //一覧画面に戻る
        return "redirect:/to_index";
    }

    /**
     * 削除完了後に一覧画面に戻る
     * @param model Modelオブジェクト
     * @return 一覧画面
     */
    @GetMapping("/to_index")
    public String toIndex(Model model){
        //一覧画面に戻る
        return index(model);
    }

    /**
     * 削除確認画面から一覧画面に戻る
     * @param model Modelオブジェクト
     * @return 一覧画面
     */
    @PostMapping(value = "/delete", params = "back")
    public String confirmDeleteBack(Model model){
        //一覧画面に戻る
        return index(model);
    }

    /**
     * 追加処理を行う画面に遷移する
     * @param model Modelオブジェクト
     * @return 入力・更新画面へのパス
     */
    @PostMapping(value = "/add", params = "next")
    public String add(Model model){
        model.addAttribute("demoForm", new DemoForm());
        return "input";
    }

    /**
     * エラーチェックを行い、エラーが無ければ確認画面に遷移し、
     * エラーがあれば入力画面のままとする
     * @param demoForm 追加・更新用Formオブジェクト
     * @param result バインド結果
     * @return 確認画面または入力画面へのパス
     */
    @PostMapping(value = "/confirm", params = "next")
    public String confirm(@Validated DemoForm demoForm, BindingResult result){
        //追加・更新用Formオブジェクトのチェック処理でエラーがある場合は、
        //入力画面のままとする
        if(result.hasErrors()){
            return "input";
        }
        //エラーが無ければ確認画面に遷移する
        return "confirm";
    }

    /**
     * 一覧画面に戻る
     * @param model Modelオブジェクト
     * @return 一覧画面の表示処理
     */
    @PostMapping(value = "/confirm", params = "back")
    public String confirmBack(Model model){
        //一覧画面に戻る
        return index(model);
    }

    /**
     * 完了画面に遷移する
     * @param demoForm 追加・更新用Formオブジェクト
     * @param result バインド結果
     * @return 完了画面
     */
    @PostMapping(value = "/send", params = "next")
    public String send(@Validated DemoForm demoForm, BindingResult result){
        //チェック処理を行い、エラーがなければ、更新・追加処理を行う
        if(result.hasErrors()){
            return "input";
        }
        demoService.createOrUpdate(demoForm);
        return "redirect:/complete";
    }

    /**
     * 完了画面に遷移する
     * @param sessionStatus セッションステータス
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
}
