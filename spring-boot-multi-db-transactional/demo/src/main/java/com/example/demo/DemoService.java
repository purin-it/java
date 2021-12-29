package com.example.demo;

import com.example.demo.mapper.ora.UserDataMapperOra;
import com.example.demo.mapper.ss.UserDataMapperSs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DemoService {

    /** 2回目の更新後氏名を定義 */
    /**
     * USER_NAME_OK:13桁39バイトで更新OK、USER_NAME_NG:41桁で更新NG
     */
    private static String USER_NAME_OK = "１２３４５６７８９０１２３";
    private static String USER_NAME_NG = "１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１";

    /**
     * Oracleのユーザーデータテーブル(user_data)へアクセスするマッパー
     */
    @Autowired
    private UserDataMapperOra userDataMapperOra;

    /**
     * SQL Serverのユーザーデータテーブル(user_data)へアクセスするマッパー
     */
    @Autowired
    private UserDataMapperSs userDataMapperSs;

    /**
     * Oracleのユーザーデータテーブル(user_data)を更新するトランザクション
     */
    // @Transactionalアノテーションで、このメソッド内のOracleのDB更新が全て成功すれば
    // コミットされ、そうでなければロールバックされる
    @Transactional(transactionManager = "txManagerOra")
    public void transUserDataOra() {
        UserData userData = userDataMapperOra.findById(Long.valueOf(1));

        // ユーザーデータが取得できなければ処理を終了する
        if (userData == null) {
            System.out.println("id=1のユーザーデータは見つかりませんでした。");
            return;
        }

        // 更新前データを表示
        System.out.println("ユーザーデータ(更新前) : " + userData.toString());

        // 氏名を更新する
        userData.setName("テスト　プリン１　更新後");
        userDataMapperOra.update(userData);

        // 更新後データを表示
        userData = userDataMapperOra.findById(Long.valueOf(1));
        System.out.println("ユーザーデータ(1回目更新後) : " + userData.toString());

        // 氏名を再度更新する
        userData.setName(USER_NAME_OK);
        userDataMapperOra.update(userData);

        // 更新後データを表示
        userData = userDataMapperOra.findById(Long.valueOf(1));
        System.out.println("ユーザーデータ(2回目更新後) : " + userData.toString());
    }

    /**
     * SQL Serverのユーザーデータテーブル(user_data)を更新するトランザクション
     */
    // @Transactionalアノテーションで、このメソッド内のSQLServerのDB更新が全て成功すれば
    // コミットされ、そうでなければロールバックされる
    @Transactional(transactionManager = "txManagerSs")
    public void transUserDataSs() {
        UserData userData = userDataMapperSs.findById(Long.valueOf(1));

        // ユーザーデータが取得できなければ処理を終了する
        if (userData == null) {
            System.out.println("id=1のユーザーデータは見つかりませんでした。");
            return;
        }

        // 更新前データを表示
        System.out.println("ユーザーデータ(更新前) : " + userData.toString());

        // 氏名を更新する
        userData.setName("テスト　プリン１　更新後");
        userDataMapperSs.update(userData);

        // 更新後データを表示
        userData = userDataMapperSs.findById(Long.valueOf(1));
        System.out.println("ユーザーデータ(1回目更新後) : " + userData.toString());

        // 氏名を再度更新する
        userData.setName(USER_NAME_OK);
        userDataMapperSs.update(userData);

        // 更新後データを表示
        userData = userDataMapperSs.findById(Long.valueOf(1));
        System.out.println("ユーザーデータ(2回目更新後) : " + userData.toString());
    }

}
