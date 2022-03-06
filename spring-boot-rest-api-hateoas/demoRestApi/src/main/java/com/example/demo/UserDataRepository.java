package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ユーザーデータテーブル(user_data)アクセス用リポジトリ
 */
// JpaRepositoryクラスを継承することによって、ユーザーデータテーブル(user_data)への
// select, insert, delete, updateを行うメソッドを自動で生成してくれる
@Repository
public interface UserDataRepository extends JpaRepository<UserData, Long> {
}
