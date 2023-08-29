package faces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage.Severity;

import common.CommonUtil;
import lombok.Data;
import lombok.ToString;

/**
 * 画面のフォーム値と画面遷移メソッドを定義.
 */
// 会話スコープを指定
// 会話スコープの開始・終了タイミングはpages.xmlで指定
@Scope(ScopeType.CONVERSATION)
// @Nameアノテーションは、JSFのXHTMLファイルから#{inputFormAction}で
// Javaクラスを参照できるようにしている(→バッキングビーン)
// ただし、org.jboss.seam.annotations.Nameクラスなので注意
@Name("inputFormAction")
@Data
@ToString(exclude={"birthMonthItems","birthDayItems","sexItems"})
public class InputFormAction implements Serializable {

    // シリアルバージョンUID
    private static final long serialVersionUID = 7283339629129432007L;

    /** 名前 */
    private String name;

    /** 生年月日_年 */
    private String birthYear;

    /** 生年月日_月 */
    private String birthMonth;

    /** 生年月日_日 */
    private String birthDay;

    /** 性別 */
    private String sex;

    /** 性別(ラベル) */
    private String sexLabel;

    /** メモ */
    private String memo;

    /** 確認チェック */
    private Boolean checked;

    /** 生年月日_月(選択リスト) */
    private List<SelectItem> birthMonthItems;

    /** 生年月日_日(選択リスト) */
    private List<SelectItem> birthDayItems;

    /** 性別(選択リスト) */
    private List<SelectItem> sexItems;

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
     * 確認画面への遷移.
     * @return 確認画面へのパス
     */
    public String confirm(){
        // 入力画面で入力チェックエラーがある場合は、画面遷移せずエラーメッセージを表示
        if(!this.validateInput()){
            return "chkerror";
        }

        // 性別(ラベル)を設定
        if(!CommonUtil.isBlank(sex)){
            this.setSexLabel(this.getSexItems().get(Integer.parseInt(this.getSex())-1).getLabel());
        }
        // Formに設定された値を出力
        System.out.println(this.toString());

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
        // 確認画面に表示された値を出力
        System.out.println(this.toString());

        // 完了画面への遷移
        return "send";
    }
    
    /**
     * 入力項目のチェック処理
     * @return チェック結果
     */
    private boolean validateInput(){
        // 戻り値として返す値
        boolean retVal = true;

        // FacesContext、UIComponentを取得
        FacesContext context = FacesContext.getCurrentInstance();
        UIComponent component = context.getViewRoot().getChildren().get(1);

        // 名前を取得する
        UIInput nameUI = (UIInput)component.findComponent("name");
        String nameSt = (String)nameUI.getValue();

        // 生年月日の年・月・日を取得する
        UIInput birthYearUI = (UIInput)component.findComponent("birthYear");
        UIInput birthMonthUI = (UIInput)component.findComponent("birthMonth");
        UIInput birthDayUI = (UIInput)component.findComponent("birthDay");
        String birthYearSt = (String)birthYearUI.getValue();
        String birthMonthSt = (String)birthMonthUI.getValue();
        String birthDaySt = (String)birthDayUI.getValue();

        // 性別を取得する
        UIInput sexUI = (UIInput)component.findComponent("sex");
        String sexSt = (String)sexUI.getValue();

        // 入力確認チェックの値を取得する
        UIInput checkedUI = (UIInput)component.findComponent("checked");
        Boolean checkedBl = (Boolean)checkedUI.getValue();

        // 名前が空白値の場合はエラーメッセージを設定する
        if(CommonUtil.isBlank(nameSt)){
            addErrorMessage("object.empty.message", "名前");
            retVal = false;

        } else {
            // 名前が1文字以上10文字以下でない場合はエラーメッセージを設定する
            if(!(nameSt.length() >= 1 && nameSt.length() <= 10)) {
                addErrorMessage("name.length.message", "名前", "1", "10");
                retVal = false;
            }
        }

        // 生年月日の年・月・日がすべて空白値の場合はエラーメッセージを設定する
        if (CommonUtil.isBlank(birthYearSt) && CommonUtil.isBlank(birthMonthSt)
                && CommonUtil.isBlank(birthDaySt)) {
            addErrorMessage("object.empty.message", "生年月日");
            retVal = false;

        } else {
            // 生年月日が存在しない日付の場合はエラーメッセージを返す
            String dateStr = birthYearSt + CommonUtil.addZero(birthMonthSt) + CommonUtil.addZero(birthDaySt);
            if(!CommonUtil.isCorrectDate(dateStr, "yyyyMMdd")){
                addErrorMessage("birthday.invalid.message");
                retVal = false;
            }
        }

        // 性別が空白値の場合はエラーメッセージを設定する
        if(CommonUtil.isBlank(sexSt)){
            addErrorMessage("object.empty.message", "性別");
            retVal = false;
        }
 
        // 入力確認チェックが未指定の場合はエラーメッセージを返す
        if(!checkedBl.booleanValue()){
            addErrorMessage("checked.notchecked.message");
            retVal = false;
        }

        return retVal;
    }

    /**
     * 引数のメッセージKeyをもつエラーメッセージを追加.
     * @param messageKey メッセージKey
     * @param params メッセージの埋め込み文字
     */
    private void addErrorMessage(String messageKey, Object... params){
        FacesMessages facesMessages = (FacesMessages) Component.getInstance("facesMessages");
        facesMessages.addFromResourceBundle(Severity.ERROR, messageKey, params);
        FacesContext.getCurrentInstance().renderResponse();
    }
}
