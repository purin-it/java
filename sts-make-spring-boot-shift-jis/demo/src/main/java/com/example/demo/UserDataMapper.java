package com.example.demo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.Collection;

@Mapper
public interface UserDataMapper {

    /**
     * ユーザーデータテーブル(user_data)から検索条件に合うデータを取得する
     * @param searchForm 検索用Formオブジェクト
     * @param pageable ページネーションオブジェクト
     * @return ユーザーデータテーブル(user_data)の検索条件に合うデータ
     */
    Collection<UserData> findBySearchForm(
            @Param("searchForm") SearchForm searchForm, @Param("pageable") Pageable pageable);

    /**
     * 指定したIDをもつユーザーデータテーブル(user_data)のデータを取得する
     * @param id ID
     * @return ユーザーデータテーブル(user_data)の指定したIDのデータ
     */
    UserData findById(Long id);

    /**
     * 指定したIDをもつユーザーデータテーブル(user_data)のデータを削除する
     * @param id ID
     */
    void deleteById(Long id);

    /**
     * 指定したユーザーデータテーブル(user_data)のデータを追加する
     * @param userData ユーザーデータテーブル(user_data)の追加データ
     */
    void create(UserData userData);

    /**
     * 指定したユーザーデータテーブル(user_data)のデータを更新する
     * @param userData ユーザーデータテーブル(user_data)の更新データ
     */
    void update(UserData userData);

    /**
     * ユーザーデータテーブル(user_data)の最大値IDを取得する
     * @return ユーザーデータテーブル(user_data)の最大値ID
     */
    long findMaxId();
}