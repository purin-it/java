package faces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import common.CommonUtil;
import common.ConversationUtil;
import jpa.UserData;
import jpa.UserDataJpa;
import lombok.Data;
import lombok.ToString;

/**
 * 画面のフォーム値と画面遷移メソッドを定義.
 */
@Named(value="inputFormAction")
// セッションスコープから会話スコープに変更
@ConversationScoped
@Data
@ToString(exclude={"birthMonthItems","birthDayItems","sexItems"})
public class InputFormAction implements Serializable {

    // シリアルバージョンUID
    private static final long serialVersionUID = 7283339629129432007L;

    /** ID */
    private String id;

    /** 名前 */
    @NotEmpty
    @Size(min=1, max=10)
    private String name;

    /** 生年月日_年 */
    private String birthYear;

    /** 生年月日_月 */
    private String birthMonth;

    /** 生年月日_日 */
    private String birthDay;

    /** 性別 */
    @NotEmpty(message="{sex.NotEmpty.message}")
    private String sex;

    /** メモ */
    private String memo;

    /** 確認チェック */
    @AssertTrue
    private Boolean checked;

    /** 生年月日_月(選択リスト) */
    private List<SelectItem> birthMonthItems;

    /** 生年月日_日(選択リスト) */
    private List<SelectItem> birthDayItems;

    /** 性別(選択リスト) */
    private List<SelectItem> sexItems;

    /** UserDataテーブルへアクセスするJPA */
    @Inject
    private UserDataJpa userDataJpa;

    /** 会話スコープマネージャー */
    @Inject
    private Conversation conv;

    /**
     * コンストラクタ生成時に選択リストの値を設定.
     */
    public InputFormAction(){
        // 生年月日_月(選択リスト)
        birthMonthItems = new ArrayList<SelectItem>();
        birthMonthItems.add(new SelectItem("", ""));
        for(Integer i = 1; i <= 12; i++){
            birthMonthItems.add(new SelectItem(String.valueOf(i), String.valueOf(i)));
        }

        // 生年月日_日(選択リスト)
        birthDayItems = new ArrayList<SelectItem>();
        birthDayItems.add(new SelectItem("", ""));
        for(Integer i = 1; i <= 31; i++){
            birthDayItems.add(new SelectItem(String.valueOf(i), String.valueOf(i)));
        }

        // 性別(選択リスト)
        sexItems = new ArrayList<SelectItem>();
        sexItems.add(new SelectItem(String.valueOf(1),"男"));
        sexItems.add(new SelectItem(String.valueOf(2),"女"));
    }

    /**
     * 入力画面への遷移(更新用).
     * @return 入力画面へのパス
     */
    public String toMod(){
    	// 会話スコープを開始
        ConversationUtil.beginConv(conv);

        // 選択されたIDをもつユーザーデータを取得・設定
        this.setSelectItem();

        // 入力画面に遷移
        return "toMod";
    }

    /**
     * 確認画面への遷移.
     * @return 確認画面へのパス
     */
    public String confirm(){
        // 確認画面に遷移
        return "confirm";
    }

    /**
     * 入力画面に戻る.
     * @return 入力画面へのパス
     */
    public String back(){
        // 入力画面に戻る
        return "back";
    }

    /**
     * 完了画面への遷移.
     * @return 完了画面へのパス
     */
    public String send(){
        // 画面の入力内容を登録または更新
        if(id != null){
            userDataJpa.update(getUserData());
        }else{
            userDataJpa.regist(getUserData());
        }

        // 会話スコープを終了
        ConversationUtil.endConv(conv);

        // 完了画面への遷移
        return "send";
    }


    /**
     * 削除確認画面への遷移(更新用).
     * @return 入力画面へのパス
     */
    public String toDel(){
        // 会話スコープを開始
        ConversationUtil.beginConv(conv);

        // 選択されたIDをもつユーザーデータを取得・設定
        this.setSelectItem();

        // 削除確認画面に遷移
        return "toDel";
    }

    /**
     * 削除確認画面から一覧画面への遷移.
     * @return 一覧画面へのパス
     */
    public String del(){
        // 画面の入力内容を削除
        userDataJpa.delete(getUserData());

        // 会話スコープを終了
        ConversationUtil.endConv(conv);

        // 一覧画面に遷移
        return "del";
    }

    /**
     * 相関チェックを実施し、エラーの場合はエラーメッセージを表示.
     * @param compSysEvent JSFシステムイベント
     */
    public void validate(ComponentSystemEvent compSysEvent) {
        UIComponent component = compSysEvent.getComponent();

        // 生年月日の年・月・日を取得する
        UIInput birthYearUI = (UIInput)component.findComponent("birthYear");
        UIInput birthMonthUI = (UIInput)component.findComponent("birthMonth");
        UIInput birthDayUI = (UIInput)component.findComponent("birthDay");
        String birthYearSt = (String)birthYearUI.getLocalValue();
        String birthMonthSt = (String)birthMonthUI.getLocalValue();
        String birthDaySt = (String)birthDayUI.getLocalValue();

        // 年・月・日がすべて空白値の場合はエラーメッセージを返す
        if(CommonUtil.isBlank(birthYearSt) && CommonUtil.isBlank(birthMonthSt)
            && CommonUtil.isBlank(birthDaySt)){
            addErrorMessage("org.hibernate.validator.constraints.NotEmpty.message", component);
            return;
        }

        // 生年月日が存在しない日付の場合はエラーメッセージを返す
        String dateStr = birthYearSt + CommonUtil.addZero(birthMonthSt) + CommonUtil.addZero(birthDaySt);
        if(!CommonUtil.isCorrectDate(dateStr, "uuuuMMdd")){
            addErrorMessage("date.Invalid.message", component);
        }
    }

    /**
     * 引数のメッセージKeyをもつエラーメッセージを追加.
     * @param messageKey メッセージKey
     * @param component JSFコンポーネント
     */
    private void addErrorMessage(String messageKey, UIComponent component){
    	FacesContext context = FacesContext.getCurrentInstance();
        String message = CommonUtil.getMessage(messageKey);
        FacesMessage facesMessage = new FacesMessage(message, message);

        facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
        context.addMessage(component.getClientId(), facesMessage);
        context.renderResponse();
    }

    /**
     * 登録時に利用するユーザー情報を生成.
     * @return ユーザー情報
     */
    private UserData getUserData(){
    	UserData userData = new UserData();
        try{
            if(this.id != null){
                userData.setId(Integer.parseInt(this.id));
            }
            userData.setName(this.getName());
            userData.setSex(this.getSex());
            userData.setMemo(this.getMemo());
            userData.setBirthYear(Integer.parseInt(this.getBirthYear()));
            userData.setBirthMonth(Integer.parseInt(this.getBirthMonth()));
            userData.setBirthDay(Integer.parseInt(this.getBirthDay()));
        }catch(Exception ex){
            System.err.println(ex);
        }
    	return userData;
    }

    /**
     * 選択されたIDをもつユーザーデータを取得・設定.
     */
    private void setSelectItem(){
    	// リクエストパラメータの値を取得
    	FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        String selectId = params.get("selectId");

        // 選択したIDをもつユーザーデータを取得
        UserData userData = userDataJpa.getById(selectId);

        // フィールドの各値に取得した値を設定
        if(userData != null){
            id = String.valueOf(userData.getId());
            name = userData.getName();
            birthYear = String.valueOf(userData.getBirthYear());
            birthMonth = String.valueOf(userData.getBirthMonth());
            birthDay = String.valueOf(userData.getBirthDay());
            sex = userData.getSex();
            memo = userData.getMemo();
        }
    }

}
