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

        System.out.println("--- SELECT * によってユーザーデータテーブル(user_data)の"
                + "データを全件取得するSQLを実行します start. ---");

        // 処理前の現在時刻を取得
        long startTime = System.currentTimeMillis();

        // SELECT * によってユーザーデータテーブル(user_data)のデータを全件取得する
        List<UserData> userDataList = userDataMapper.findAllByAsta();

        // 処理後の現在時刻を取得
        long endTime = System.currentTimeMillis();

        System.out.println("取得件数 : " + userDataList.size());
        System.out.println("処理時間：" + (endTime - startTime) + " ms");

        System.out.println("--- SELECT * によってユーザーデータテーブル(user_data)の"
                + "データを全件取得するSQLを実行します end. ---");
        System.out.println();

        System.out.println("--- SELECT (カラム名) によってユーザーデータテーブル(user_data)の"
                + "データを全件取得するSQLを実行します start. ---");

        // 処理前の現在時刻を取得
        startTime = System.currentTimeMillis();

        // SELECT (カラム名) によってユーザーデータテーブル(user_data)のデータを全件取得する
        userDataList = userDataMapper.findAllByColumn();

        // 処理後の現在時刻を取得
        endTime = System.currentTimeMillis();

        System.out.println("取得件数 : " + userDataList.size());
        System.out.println("処理時間：" + (endTime - startTime) + " ms");

        System.out.println("--- SELECT (カラム名) によってユーザーデータテーブル(user_data)の"
                + "データを全件取得するSQLを実行します end. ---");
        System.out.println();

        System.out.println("--- SELECT count(*) によってユーザーデータテーブル(user_data)の"
                + "データ件数を取得するSQLを実行します start. ---");

        // 処理前の現在時刻を取得
        startTime = System.currentTimeMillis();

        // SELECT count(*) によってユーザーデータテーブル(user_data)のデータ件数を取得する
        Long cntUserData = userDataMapper.countAllByAsta();

        // 処理後の現在時刻を取得
        endTime = System.currentTimeMillis();

        System.out.println("取得件数 : " + cntUserData);
        System.out.println("処理時間：" + (endTime - startTime) + " ms");

        System.out.println("--- SELECT count(*) によってユーザーデータテーブル(user_data)の"
                + "データ件数を取得するSQLを実行します end. ---");
        System.out.println();

        System.out.println("--- SELECT count((カラム名)) によってユーザーデータテーブル(user_data)の"
                + "データ件数を取得するSQLを実行します start. ---");

        // 処理前の現在時刻を取得
        startTime = System.currentTimeMillis();

        // SELECT count((カラム名)) によってユーザーデータテーブル(user_data)のデータ件数を取得する
        cntUserData = userDataMapper.countAllByColumn();

        // 処理後の現在時刻を取得
        endTime = System.currentTimeMillis();

        System.out.println("取得件数 : " + cntUserData);
        System.out.println("処理時間：" + (endTime - startTime) + " ms");

        System.out.println("--- SELECT count((カラム名)) によってユーザーデータテーブル(user_data)の"
                + "データ件数を取得するSQLを実行します end. ---");
    }
}
