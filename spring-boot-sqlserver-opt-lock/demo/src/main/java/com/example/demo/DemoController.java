package com.example.demo;
 
import com.microsoft.sqlserver.jdbc.SQLServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.support.SessionStatus;
import java.util.ArrayList;
import java.util.List;
 
@Controller
@SessionAttributes(types = {DemoForm.class})
public class DemoController {

    /** Demoサービスクラスへのアクセス */
    @Autowired
    private DemoService demoService;

    /** DB取得時ロックエラーメッセージ */
    private static final String DB_LOCK_ERROR = "ロック要求がタイムアウトしました。";
 
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
     * @param demoForm Formオブジェクト
     * @param result バインド結果
     * @return 一覧画面の表示処理
     */
    @PostMapping(value = "/delete", params = "next")
    public String delete(DemoForm demoForm, BindingResult result){
        //指定したユーザーデータを削除
        try{
            int updCnt = demoService.deleteById(demoForm);
            if(updCnt != 1){
                //更新件数が1件でない場合、楽観ロックエラーとし、削除確認画面に遷移
                result.reject("exception.optimistic-locking-failure");
                return "confirm_delete";
            }
        }catch(RuntimeException ex){
            //OptimisticLockingFailureException(楽観ロックエラーの場合)
            if("OptimisticLockingFailureException".equals(ex.getMessage())) {
                result.reject("exception.optimistic-locking-failure");
                //DB更新時ロック取得時エラーが発生した場合
            } else if(ex.getCause() instanceof SQLServerException
                    && DB_LOCK_ERROR.equals(ex.getCause().getMessage())){
                result.reject("exception.row-lock-failure");
                //上記エラー以外の場合
            } else {
                result.reject("exception.db-update-failure");
            }
            //エラー発生時は、削除確認画面に遷移
            return "confirm_delete";
        }
        //一覧画面に遷移
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
        //チェック処理を行い、画面遷移する
        return demoService.checkForm(demoForm, result, "confirm");
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
     * @param result バインド結果
     * @return 完了画面または確認画面
     */
    @PostMapping(value = "/send", params = "next")
    public String send(@Validated DemoForm demoForm, BindingResult result){
        //チェック処理を行い、エラーがなければ、更新・追加処理を行う
        String normalPath = "redirect:/complete";
        String checkPath = demoService.checkForm(demoForm, result, "redirect:/complete");
        if(normalPath.equals(checkPath)){
            try{
                int updCnt = demoService.createOrUpdate(demoForm);
                if(updCnt != 1){
                    //更新件数が1件でない場合、楽観ロックエラーとし、確認画面に遷移
                    result.reject("exception.optimistic-locking-failure");
                    return "confirm";
                }
            }catch(RuntimeException ex){
                //OptimisticLockingFailureException(楽観ロックエラーの場合)
                if("OptimisticLockingFailureException".equals(ex.getMessage())) {
                    result.reject("exception.optimistic-locking-failure");
                    //DB更新時ロック取得時エラーが発生した場合
                } else if(ex.getCause() instanceof SQLServerException
                        && DB_LOCK_ERROR.equals(ex.getCause().getMessage())){
                    result.reject("exception.row-lock-failure");
                    //上記エラー以外の場合
                } else {
                    result.reject("exception.db-update-failure");
                }
                //エラー発生時は、確認画面に遷移
                return "confirm";
            }
        }
        return checkPath;
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