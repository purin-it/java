package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import org.springframework.data.domain.Pageable;
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
     * 検索用Formオブジェクトを初期化して返却する
     * @return 検索用Formオブジェクト
     */
    @ModelAttribute("searchForm")
    public SearchForm createSearchForm(){
        SearchForm searchForm = new SearchForm();
        return searchForm;
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
    @RequestMapping("/search")
    public String search(SearchForm searchForm, Model model, BindingResult result){
        //検索用Formオブジェクトのチェック処理
        String returnVal = demoService.checkSearchForm(searchForm, result);
        if(returnVal != null){
            return returnVal;
        }
        //現在ページ数を1ページ目に設定し、一覧画面に遷移する
        searchForm.setCurrentPageNum(1);
        return movePageInList(model, searchForm);
    }

    /**
     * 一覧画面で「先頭へ」リンク押下時に次ページを表示する
     * @param searchForm 検索用Formオブジェクト
     * @param model Modelオブジェクト
     * @return 一覧画面へのパス
     */
    @RequestMapping("/firstPage")
    public String firstPage(SearchForm searchForm, Model model){
        //現在ページ数を先頭ページに設定する
        searchForm.setCurrentPageNum(1);
        return movePageInList(model, searchForm);
    }

    /**
     * 一覧画面で「前へ」リンク押下時に次ページを表示する
     * @param searchForm 検索用Formオブジェクト
     * @param model Modelオブジェクト
     * @return 一覧画面へのパス
     */
    @RequestMapping("/backPage")
    public String backPage(SearchForm searchForm, Model model){
        //現在ページ数を前ページに設定する
        searchForm.setCurrentPageNum(searchForm.getCurrentPageNum() - 1);
        return movePageInList(model, searchForm);
    }

    /**
     * 一覧画面で「次へ」リンク押下時に次ページを表示する
     * @param searchForm 検索用Formオブジェクト
     * @param model Modelオブジェクト
     * @return 一覧画面へのパス
     */
    @RequestMapping("/nextPage")
    public String nextPage(SearchForm searchForm, Model model){
        //現在ページ数を次ページに設定する
        searchForm.setCurrentPageNum(searchForm.getCurrentPageNum() + 1);
        return movePageInList(model, searchForm);
    }

    /**
     * 一覧画面で「最後へ」リンク押下時に次ページを表示する
     * @param searchForm 検索用Formオブジェクト
     * @param model Modelオブジェクト
     * @return 一覧画面へのパス
     */
    @RequestMapping("/lastPage")
    public String lastPage(SearchForm searchForm, Model model){
        //現在ページ数を最終ページに設定する
        searchForm.setCurrentPageNum(demoService.getAllPageNum(searchForm));
        return movePageInList(model, searchForm);
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
     * @param demoForm 追加・更新用Formオブジェクト
     * @param searchForm 検索用Formオブジェクト
     * @param model Modelオブジェクト
     * @return 一覧画面の表示処理
     */
    @RequestMapping(value = "/delete", params = "next")
    public String delete(DemoForm demoForm, SearchForm searchForm, Model model){
        //指定したユーザーデータを削除
        demoService.deleteById(demoForm.getId());
        //一覧画面に戻り、1ページ目のリストを表示する
        searchForm.setCurrentPageNum(1);
        return movePageInList(model, searchForm);
    }

    /**
     * 削除確認画面から一覧画面に戻る
     * @param model Modelオブジェクト
     * @param searchForm 検索用Formオブジェクト
     * @return 一覧画面
     */
    @RequestMapping(value = "/delete", params = "back")
    public String confirmDeleteBack(Model model, SearchForm searchForm){
        //一覧画面に戻る
        return movePageInList(model, searchForm);
    }

    /**
     * 追加処理を行う画面に遷移する
     * @param model Modelオブジェクト
     * @return 入力・更新画面へのパス
     */
    @RequestMapping(value = "/add", params = "next")
    public String add(Model model){
        model.addAttribute("demoForm", new DemoForm());
        return "input";
    }

    /**
     * 追加処理を行う画面から検索画面に戻る
     * @return 検索画面へのパス
     */
    @RequestMapping(value = "/add", params = "back")
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
    @RequestMapping(value = "/confirm", params = "next")
    public String confirm(@Valid DemoForm demoForm, BindingResult result){
        //生年月日の日付チェック処理を行い、画面遷移する
        return demoService.checkForm(demoForm, result);
    }

    /**
     * 一覧画面に戻る
     * @param model Modelオブジェクト
     * @param searchForm 検索用Formオブジェクト
     * @return 一覧画面の表示処理
     */
    @RequestMapping(value = "/confirm", params = "back")
    public String confirmBack(Model model, SearchForm searchForm){
        //一覧画面に戻る
        return movePageInList(model, searchForm);
    }

    /**
     * 完了画面に遷移する
     * @param demoForm 追加・更新用Formオブジェクト
     * @param sessionStatus セッションステータス
     * @return 完了画面
     */
    @RequestMapping(value = "/send", params = "next")
    public String send(DemoForm demoForm, SessionStatus sessionStatus){
        //ユーザーデータがあれば更新し、無ければ削除
        demoService.createOrUpdate(demoForm);
        //メール送信を非同期で行う
        demoService.sendCompleteMail(demoForm);
        //セッションオブジェクトを破棄
        sessionStatus.setComplete();
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
     * 一覧画面に戻り、指定した現在ページのリストを表示する
     * @param model Modelオブジェクト
     * @param searchForm 検索用Formオブジェクト
     * @return 一覧画面の表示処理
     */
    private String movePageInList(Model model, SearchForm searchForm){
        //現在ページ数, 総ページ数を設定
        model.addAttribute("currentPageNum", searchForm.getCurrentPageNum());
        model.addAttribute("allPageNum", demoService.getAllPageNum(searchForm));
        //ページング用オブジェクトを生成し、現在ページのユーザーデータリストを取得
        Pageable pageable = demoService.getPageable(searchForm.getCurrentPageNum());
        List<DemoForm> demoFormList = demoService.demoFormList(searchForm, pageable);
        //ユーザーデータリストを更新
        model.addAttribute("demoFormList", demoFormList);
        return "list";
    }
}
