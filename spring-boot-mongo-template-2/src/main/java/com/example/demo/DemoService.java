package com.example.demo;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface DemoService {

    /**
     * ユーザーデータリストを取得
     * @param searchForm 検索用Formオブジェクト
     * @param pageable ページネーションオブジェクト
     * @return ユーザーデータリスト
     */
    List<DemoForm> demoFormList(SearchForm searchForm, Pageable pageable);

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

    /**
     * ユーザー検索時に利用するページング用オブジェクトを生成する
     * @param pageNumber ページ番号
     * @return ページング用オブジェクト
     */
    Pageable getPageable(int pageNumber);

    /**
     * 一覧画面の全ページ数を取得する
     * @param searchForm 検索用Formオブジェクト
     * @return 全ページ数
     */
    int getAllPageNum(SearchForm searchForm);
}
