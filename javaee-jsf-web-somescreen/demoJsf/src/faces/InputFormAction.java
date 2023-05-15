package faces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import common.CommonUtil;

/**
 * 画面のフォーム値と画面遷移メソッドを定義.
 */
// @Namedアノテーションは、JSFのXHTMLファイルから#{inputFormAction}で
// Javaクラスを参照できるようにしている(→バッキングビーン)
// @SessionScopedアノテーションは、このバッキングビーンの生存期間を
// セッションに設定している
@Named(value="inputFormAction")
@SessionScoped
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
    private String checked;

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
        // 性別(ラベル)を設定
        if(!CommonUtil.isBlank(sex)){
        	this.setSexLabel(this.getSexItems().get(Integer.parseInt(this.getSex())-1).getLabel());
        }
        // Formに設定された値を出力
        System.out.println(this.toString());

        // 確認画面に遷移、ただし、遷移先URLを正しく表示し画面遷移を安定させるため、
        // ?faces-redirect=trueを付与し、フォワードからリダイレクトに変更している
        return "confirm.xhtml?faces-redirect=true";
    }

    /**
     * 入力画面に戻る.
     * @return 入力画面へのパス
     */
    public String back(){
    	// 入力画面に戻る
        return "input.xhtml?faces-redirect=true";
    }

    /**
     * 完了画面への遷移.
     * @return 完了画面へのパス
     */
    public String send(){
    	// 確認画面に表示された値を出力
        System.out.println(this.toString());

        // 完了画面に遷移
        return "complete.xhtml?faces-redirect=true";
    }

    @Override
    public String toString() {
        return "InputForm [name=" + name + ", birthYear=" + birthYear + ", birthMonth=" + birthMonth + ", birthDay="
              + birthDay + ", sex=" + sex + " sexLabel=" + sexLabel +  ", memo=" + memo + ", checked=" + checked + "]";
    }

    // 以下はGetter/Setterメソッド
    // JSFのXHTMLファイルから各メンバ変数にアクセスするために必要
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public String getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(String birthMonth) {
        this.birthMonth = birthMonth;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSexLabel() {
        return sexLabel;
    }

    public void setSexLabel(String sexLabel) {
        this.sexLabel = sexLabel;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public List<SelectItem> getBirthMonthItems() {
        return birthMonthItems;
    }

    public void setBirthMonthItems(List<SelectItem> birthMonthItems) {
        this.birthMonthItems = birthMonthItems;
    }

    public List<SelectItem> getBirthDayItems() {
        return birthDayItems;
    }

    public void setBirthDayItems(List<SelectItem> birthDayItems) {
        this.birthDayItems = birthDayItems;
    }

    public List<SelectItem> getSexItems() {
        return sexItems;
    }

    public void setSexItems(List<SelectItem> sexItems) {
        this.sexItems = sexItems;
    }

}
