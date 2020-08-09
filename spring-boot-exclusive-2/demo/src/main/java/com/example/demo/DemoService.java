package com.example.demo;

import org.springframework.validation.BindingResult;

import java.util.List;

public interface DemoService {

    /**
     * ユーザーデータリストを取得
     * @return ユーザーデータリスト
     */
    List<DemoForm> demoFormList();

    /**
     * 引数のIDに対応するユーザーデータを取得
     * @param id ID
     * @return ユーザーデータ
     */
    DemoForm findById(String id);

    /**
     * 引数のIDに対応するユーザーデータを削除
     * @param id ID
     */
    void deleteById(String id);

    /**
     * 引数のユーザーデータがあれば更新し、無ければ削除
     * @param demoForm フォームオブジェクト
     */
    void createOrUpdate(DemoForm demoForm);

    /**
     * フォームオブジェクトのチェック処理を行い、画面遷移先を返す
     * @param demoForm フォームオブジェクト
     * @param result バインド結果
     * @param normalPath 正常時の画面遷移先
     * @return 画面遷移先
     */
    String checkForm(DemoForm demoForm, BindingResult result, String normalPath);

}
