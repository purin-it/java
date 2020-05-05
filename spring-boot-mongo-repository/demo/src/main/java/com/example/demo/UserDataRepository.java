package com.example.demo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository extends MongoRepository<UserData, String> {

    /**
     * ユーザーデータから、IDが最大のデータを取得する
     * @return IDが最大のユーザーデータ
     */
    UserData findTopByOrderByIdDesc();
}
