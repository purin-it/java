package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// JpaRepositoryクラスを継承することによって、ユーザーデータテーブル(user_data)への
// select, insert, delete, updateを行うメソッドを自動で生成してくれる
@Repository
public interface UserDataRepository extends JpaRepository<UserData, Long> {

    /**
     * 検索IDを引数にUserDataオブジェクトを取得する
     * @param id 検索ID
     * @return UserDataオブジェクト
     */
    UserData findUserDataById(Long id);

}