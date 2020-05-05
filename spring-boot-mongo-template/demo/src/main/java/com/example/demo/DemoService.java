package com.example.demo;

import java.util.List;

public interface DemoService {

    /**
     * ユーザーデータリストを全件取得
     * @return ユーザーデータリスト
     */
    List<DemoForm> demoFormList();

    /**
     * 引数のIDに対応するユーザーデータを取得
     * @param pKeyID MongoDBの主キー
     * @return ユーザーデータ
     */
    DemoForm findByPKeyId(String pKeyID);

    /**
     * 引数のユーザーデータを削除
     * @param demoForm 追加・更新用Formオブジェクト
     */
    void delete(DemoForm demoForm);

    /**
     * 引数のユーザーデータがあれば更新し、無ければ追加
     * @param demoForm 追加・更新用Formオブジェクト
     */
    void createOrUpdate(DemoForm demoForm);
}
