package faces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import common.CommonUtil;
import lombok.Data;
import lombok.ToString;

/**
 * 画面のフォーム値と画面遷移メソッドを定義.
 */
// @Namedアノテーションは、JSFのXHTMLファイルから#{inputFormAction}で
// Javaクラスを参照できるようにしている(→バッキングビーン)
// @SessionScopedアノテーションは、このバッキングビーンの生存期間を
// セッションに設定している
@Named(value="inputFormAction")
@SessionScoped

// 以下はLombokのアノテーション
//「@Data」アノテーションを付与すると、このクラス内の全フィールドに対する
// Getterメソッド・Setterメソッドにアクセスができる
@Data
//「@ToString」アノテーションを用いて、exclude属性で指定した項目以外の値を、
// toStringメソッド呼出時に出力することができる
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

}
