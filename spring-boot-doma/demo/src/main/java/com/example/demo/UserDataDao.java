package com.example.demo;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import java.util.List;

@ConfigAutowireable
@Dao
public interface UserDataDao {

    /**
     * ユーザーデータテーブル(user_data)を全件取得する
     * @return ユーザーデータテーブル(user_data)を全データ
     */
    @Select
    List<UserData> findAll();

    /**
     * 指定したIDをもつユーザーデータテーブル(user_data)のデータを取得する
     * @param id ID
     * @return ユーザーデータテーブル(user_data)の指定したIDのデータ
     */
    @Select
    UserData findById(Long id);

    /**
     * 指定したIDをもつユーザーデータテーブル(user_data)のデータを削除する
     * @param userData ユーザーデータテーブル(user_data)の削除データ
     */
    @Delete
    int deleteById(UserData userData);

    /**
     * 指定したユーザーデータテーブル(user_data)のデータを追加する
     * @param userData ユーザーデータテーブル(user_data)の追加データ
     */
    @Insert
    int create(UserData userData);

    /**
     * 指定したユーザーデータテーブル(user_data)のデータを更新する
     * @param userData ユーザーデータテーブル(user_data)の更新データ
     */
    @Update
    int update(UserData userData);

    /**
     * ユーザーデータテーブル(user_data)の最大値IDを取得する
     * @return ユーザーデータテーブル(user_data)の最大値ID
     */
    @Select
    long findMaxId();
}

