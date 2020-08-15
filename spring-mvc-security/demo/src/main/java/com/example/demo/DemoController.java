package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes(types = {DemoForm.class, SearchForm.class})
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
        List<DemoForm> demoFormList = new ArrayList<DemoForm>();
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
     * 検索用Formオブジェクトを初期化して返却する
     * @return 検索用Formオブジェクト
     */
    @ModelAttribute("searchForm")
    public SearchForm createSearchForm(){
        SearchForm searchForm = new SearchForm();
        return searchForm;
    }
    
    /**
     * ログイン画面に遷移する
     * @return ログイン画面へのパス
     */
    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    /**
     * 初期表示(検索)画面に遷移する
     * @return 検索画面へのパス
     */
    @RequestMapping("/")
    public String index(){
        return "search";
    }

    /**
     * 検索処理を行い、一覧画面に遷移する
     * @param searchForm 検索用Formオブジェクト
     * @param model Modelオブジェクト
     * @param result バインド結果
     * @return 一覧画面へのパス
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(SearchForm searchForm, Model model, BindingResult result){
        //検索用Formオブジェクトのチェック処理
        String returnVal = demoService.checkSearchForm(searchForm, result);
        if(returnVal != null){
            return returnVal;
        }
        //ユーザーデータリストを取得
        List<DemoForm> demoFormList = demoService.demoFormList(searchForm);
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
    @RequestMapping(value = "/update", method = RequestMethod.GET)
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
    @RequestMapping(value = "/delete_confirm", method = RequestMethod.GET)
    public String delete_confirm(@RequestParam("id") String id, Model model){
        //削除対象のユーザーデータを取得
        DemoForm demoForm = demoService.findById(id);
        //ユーザーデータを更新
        model.addAttribute("demoForm", demoForm);
        return "confirm_delete";
    }

    /**
     * 削除処理を行う
     * @param demoForm 追加・更新用Formオブジェクト
     * @return 一覧画面の表示処理
     */
    @RequestMapping(value = "/delete", params = "next", method = RequestMethod.POST)
    public String delete(DemoForm demoForm){
        //指定したユーザーデータを削除
        demoService.deleteById(demoForm.getId());
        //一覧画面に遷移
        return "redirect:/to_index";
    }
    
    /**
     * 削除完了後に一覧画面に戻る
     * @param searchForm 検索用Formオブジェクト
     * @param model Modelオブジェクト
     * @return 一覧画面
     */
    @RequestMapping(value = "/to_index", method = RequestMethod.GET)
    public String toIndex(SearchForm searchForm, Model model){
        //一覧画面に遷移
        //ユーザーデータリストを取得
        List<DemoForm> demoFormList = demoService.demoFormList(searchForm);
        //ユーザーデータリストを更新
        model.addAttribute("demoFormList", demoFormList);
        return "list";
    }

    /**
     * 削除確認画面から一覧画面に戻る
     * @param model Modelオブジェクト
     * @param searchForm 検索用Formオブジェクト
     * @return 一覧画面
     */
    @RequestMapping(value = "/delete", params = "back", method = RequestMethod.POST)
    public String confirmDeleteBack(Model model, SearchForm searchForm){
        //一覧画面に遷移
        //ユーザーデータリストを取得
        List<DemoForm> demoFormList = demoService.demoFormList(searchForm);
        //ユーザーデータリストを更新
        model.addAttribute("demoFormList", demoFormList);
        return "list";
    }

    /**
     * 追加処理を行う画面に遷移する
     * @param model Modelオブジェクト
     * @return 入力・更新画面へのパス
     */
    @RequestMapping(value = "/add", params = "next", method = RequestMethod.POST)
    public String add(Model model){
        model.addAttribute("demoForm", new DemoForm());
        return "input";
    }

    /**
     * 追加処理を行う画面から検索画面に戻る
     * @return 検索画面へのパス
     */
    @RequestMapping(value = "/add", params = "back", method = RequestMethod.POST)
    public String addBack(){
        return "search";
    }

    /**
     * エラーチェックを行い、エラーが無ければ確認画面に遷移し、
     * エラーがあれば入力画面のままとする
     * @param demoForm 追加・更新用Formオブジェクト
     * @param result バインド結果
     * @return 確認画面または入力画面へのパス
     */
    @RequestMapping(value = "/confirm", params = "next", method = RequestMethod.POST)
    public String confirm(@Valid DemoForm demoForm, BindingResult result){
        //生年月日の日付チェック処理を行い、画面遷移する
        return demoService.checkForm(demoForm, result, "confirm");
    }

    /**
     * 一覧画面に戻る
     * @param model Modelオブジェクト
     * @param searchForm 検索用Formオブジェクト
     * @return 一覧画面の表示処理
     */
    @RequestMapping(value = "/confirm", params = "back", method = RequestMethod.POST)
    public String confirmBack(Model model, SearchForm searchForm){
        //ユーザーデータリストを取得
        List<DemoForm> demoFormList = demoService.demoFormList(searchForm);
        //ユーザーデータリストを更新
        model.addAttribute("demoFormList", demoFormList);
        return "list";
    }

    /**
     * 完了画面に遷移する
     * @param demoForm 追加・更新用Formオブジェクト
     * @param result バインド結果
     * @return 完了画面
     */
    @RequestMapping(value = "/send", params = "next", method = RequestMethod.POST)
    public String send(DemoForm demoForm, BindingResult result){
    	//チェック処理を行い、エラーがなければ、更新・追加処理を行う
        String normalPath = "redirect:/complete";
        String checkPath = demoService.checkForm(demoForm, result, "redirect:/complete");
        if(normalPath.equals(checkPath)){
            demoService.createOrUpdate(demoForm);
        }
        return checkPath;
    }
    
    /**
     * 完了画面に遷移する
     * @param sessionStatus セッションステータス
     * @return 完了画面
     */
    @RequestMapping(value = "/complete", method = RequestMethod.GET)
    public String complete(SessionStatus sessionStatus){
        //セッションオブジェクトを破棄
        sessionStatus.setComplete();
        return "complete";
    }

    /**
     * 入力画面に戻る
     * @return 入力画面
     */
    @RequestMapping(value = "/send", params = "back", method = RequestMethod.POST)
    public String sendBack(){
        return "input";
    }

}
