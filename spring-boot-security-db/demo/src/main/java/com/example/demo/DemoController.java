package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
     * Spring-Security用のユーザーアカウント情報を
     * 取得・設定するサービスへのアクセス
     */
    @Autowired
    private UserPassAccountService userDetailsService;

    /**
     * パスワードをBCryptで暗号化するクラスへのアクセス
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

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
     * ログイン用ユーザーのデータを登録する
     * @return ログイン画面へのパス
     */
    @GetMapping("/login")
    public String login(){
        //ユーザー名「user」、パスワード「pass」をユーザーパスワードデータテーブル(user_pass)
        //へ登録する。その際、パスワードはBCryptで暗号化する
        userDetailsService.registerUser(
                "user", passwordEncoder.encode("pass"));
        //ログイン画面に遷移する
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
     * @param result バインド結果
     * @param model Modelオブジェクト
     * @return 一覧画面へのパス
     */
    @PostMapping("/search")
    public String search(@Validated SearchForm searchForm
            , BindingResult result, Model model){
        //検索用Formオブジェクトのチェック処理でエラーがある場合は、
        //検索画面のままとする
        if(result.hasErrors()){
            return "search";
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
    @GetMapping("/firstPage")
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
    @GetMapping("/backPage")
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
    @GetMapping("/nextPage")
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
    @GetMapping("/lastPage")
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
    @GetMapping("/update")
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
    @GetMapping("/delete_confirm")
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
    @PostMapping(value = "/delete", params = "next")
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
    @GetMapping("/to_index")
    public String toIndex(SearchForm searchForm, Model model){
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
    @PostMapping(value = "/delete", params = "back")
    public String confirmDeleteBack(Model model, SearchForm searchForm){
        //一覧画面に戻る
        return movePageInList(model, searchForm);
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
     * 追加処理を行う画面から検索画面に戻る
     * @return 検索画面へのパス
     */
    @PostMapping(value = "/add", params = "back")
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
     * @param searchForm 検索用Formオブジェクト
     * @return 一覧画面の表示処理
     */
    @PostMapping(value = "/confirm", params = "back")
    public String confirmBack(Model model, SearchForm searchForm){
        //一覧画面に戻る
        return movePageInList(model, searchForm);
    }

    /**
     * 完了画面に遷移する
     * @param demoForm 追加・更新用Formオブジェクト
     * @param result バインド結果
     * @return 完了画面
     */
    @PostMapping(value = "/send", params = "next")
    public String send(DemoForm demoForm, BindingResult result){
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
