package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DemoServiceSub {

    /**
     * 追加するユーザーデータの数
     */
    private static final int CNT_USER_DATA = 100000;

    /**
     * 一括追加する場合のレコード数
     */
    private static final int CNT_INSERT_MULTI = 1000;

    /**
     * ユーザーデータテーブル(user_data)へアクセスするマッパー
     */
    @Autowired
    private UserDataMapper userDataMapper;

    /**
     * 性能検証を行うための個別サービス
     */
    public void verifyPerformanceEach() {
        // 追加するユーザーデータリストを生成する
        List<UserData> userDataList = new ArrayList<>();
        for (int i = 0; i < CNT_USER_DATA; i++) {
            userDataList.add(makeUserData(i));
        }

        // ユーザーデータテーブル(user_data)を全件削除する
        userDataMapper.truncateData();

        System.out.println("--- ユーザーデータテーブル(user_data)にデータを追加する"
                + "処理を1レコードずつ実施します start. ---");

        // 処理前の現在時刻を取得
        long startTime = System.currentTimeMillis();

        // ユーザーデータテーブル(user_data)にデータを追加する処理を1レコードずつ実施
        for (int i = 0; i < CNT_USER_DATA; i++) {
            userDataMapper.insertDataOne(userDataList.get(i));
        }

        // 処理後の現在時刻を取得
        long endTime = System.currentTimeMillis();

        System.out.println("処理件数 : " + userDataMapper.countAll());
        System.out.println("処理時間：" + (endTime - startTime) + " ms");
        System.out.println("--- ユーザーデータテーブル(user_data)にデータを追加する"
                + "処理を1レコードずつ実施します end. ---");
        System.out.println();

        // ユーザーデータテーブル(user_data)を全件削除する
        userDataMapper.truncateData();

        // CNT_INSERT_MULTIレコードずつ追加する際のループ数を算出する
        int cntInsertLoop = (CNT_USER_DATA + CNT_INSERT_MULTI - 1) / CNT_INSERT_MULTI;

        System.out.println("--- ユーザーデータテーブル(user_data)にデータを追加する処理を"
                + CNT_INSERT_MULTI + "レコードずつ実施します start. ---");

        // 処理前の現在時刻を取得
        startTime = System.currentTimeMillis();

        // ユーザーデータテーブル(user_data)にデータを追加する処理をCNT_INSERT_MULTIレコードずつ実施
        for (int i = 0; i < cntInsertLoop; i++) {
            userDataMapper.insertDataMulti(
                    userDataList.subList(CNT_INSERT_MULTI * i
                            , Math.min(CNT_INSERT_MULTI * (i + 1), CNT_USER_DATA)));
        }

        // 処理後の現在時刻を取得
        endTime = System.currentTimeMillis();

        System.out.println("処理件数 : " + userDataMapper.countAll());
        System.out.println("処理時間：" + (endTime - startTime) + " ms");
        System.out.println("--- ユーザーデータテーブル(user_data)にデータを追加する処理を"
                + CNT_INSERT_MULTI + "レコードずつ実施します end. ---");
        System.out.println();
    }

    /**
     * 引数のIDをもつユーザーデータを生成する
     * @param id ID
     * @return ユーザーデータ
     */
    private UserData makeUserData(int id) {
        UserData userData = new UserData();
        userData.setId(id);
        userData.setName("テスト　プリン" + id);
        userData.setSex(Integer.toString(id % 2 + 1));
        userData.setBirthY(2012);
        userData.setBirthM(1);
        userData.setBirthD(15);
        userData.setMemo("テスト" + id);
        return userData;
    }
}
