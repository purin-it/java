package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoServiceSub {

    /**
     * ユーザーデータテーブル(user_data)へアクセスするマッパー
     */
    @Autowired
    private UserDataMapper userDataMapper;

    /**
     * 性能検証を行うための個別サービス
     */
    public void verifyPerformanceEach() {
        // 初回DB接続時は時間がかかるため、あえて性能測定対象外のSQLを1度実行しておく
        userDataMapper.findById(Long.valueOf(1));

        System.out.println("--- メモを条件にユーザーデータテーブル(user_data)の"
                + "データを取得するSQLを実行します start. ---");

        // 処理前の現在時刻を取得
        long startTime = System.currentTimeMillis();

        // メモを条件にユーザーデータテーブルのデータを取得
        List<UserData> userDataList = userDataMapper.findByMemo();

        // 処理後の現在時刻を取得
        long endTime = System.currentTimeMillis();

        System.out.println("取得件数 : " + userDataList.size());
        System.out.println("処理時間：" + (endTime - startTime) + " ms");
        System.out.println("--- メモを条件にユーザーデータテーブル(user_data)の"
                + "データを取得するSQLを実行します end. ---");
        System.out.println();

        System.out.println("--- 名前を条件にユーザーデータテーブル(user_data)の"
                + "データを取得するSQLを実行します start. ---");

        // 処理前の現在時刻を取得
        startTime = System.currentTimeMillis();

        // 名前を条件にユーザーデータテーブルのデータを取得
        userDataList = userDataMapper.findByName();

        // 処理後の現在時刻を取得
        endTime = System.currentTimeMillis();

        System.out.println("取得件数 : " + userDataList.size());
        System.out.println("処理時間：" + (endTime - startTime) + " ms");
        System.out.println("--- 名前を条件にユーザーデータテーブル(user_data)の"
                + "データを取得するSQLを実行します end. ---");
        System.out.println();
    }
}
