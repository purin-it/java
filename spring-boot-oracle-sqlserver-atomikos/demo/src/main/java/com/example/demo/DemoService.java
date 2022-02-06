package com.example.demo;

import com.example.demo.mapper.ora.UserDataMapperOra;
import com.example.demo.mapper.ss.UserDataMapperSs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.UserTransaction;

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
     * Atomikosを利用した場合のトランザクション管理
     */
    @Autowired
    private JtaTransactionManager jtaTransactionManager;

    /**
     * Atomikosを利用して、OracleとSQL Serverのユーザーデータテーブル(user_data)を
     * 更新するトランザクション
     * @throws Exception 任意例外
     */
    public void transUserData() throws Exception {
        //トランザクションを開始する
        UserTransaction userTransaction = jtaTransactionManager.getUserTransaction();
        userTransaction.begin();

        try {
            // id=1のユーザーデータを取得する
            UserData userDataOra = userDataMapperOra.findById(Long.valueOf(1));
            UserData userDataSs = userDataMapperSs.findById(Long.valueOf(1));

            // ユーザーデータが取得できなければ処理を終了する
            if (userDataOra == null || userDataSs == null) {
                System.out.println("OracleまたはSQL Serverのいずれかで、id=1のユーザーデータは見つかりませんでした。");
                return;
            }

            // 更新前データを表示
            System.out.println("ユーザーデータ(Oracle更新前) : " + userDataOra.toString());
            System.out.println("ユーザーデータ(SQL Server更新前) : " + userDataSs.toString());

            // 氏名を更新する
            userDataOra.setName("テスト　プリン１　更新後");
            userDataSs.setName("テスト　プリン１　更新後");
            userDataMapperOra.update(userDataOra);
            userDataMapperSs.update(userDataSs);

            // 更新後データを表示
            userDataOra = userDataMapperOra.findById(Long.valueOf(1));
            userDataSs = userDataMapperSs.findById(Long.valueOf(1));
            System.out.println("ユーザーデータ(Oracle1回目更新後) : " + userDataOra.toString());
            System.out.println("ユーザーデータ(SQL Server1回目更新後) : " + userDataSs.toString());

            // 氏名を再度更新する
            userDataOra.setName(USER_NAME_OK);
            userDataSs.setName(USER_NAME_OK);
            userDataMapperOra.update(userDataOra);
            userDataMapperSs.update(userDataSs);

            // 更新後データを表示
            userDataOra = userDataMapperOra.findById(Long.valueOf(1));
            userDataSs = userDataMapperSs.findById(Long.valueOf(1));
            System.out.println("ユーザーデータ(Oracle2回目更新後) : " + userDataOra.toString());
            System.out.println("ユーザーデータ(SQL Server2回目更新後) : " + userDataSs.toString());

            //トランザクションをコミットする
            userTransaction.commit();

        } catch (Exception ex) {
            System.err.println(ex);

            //トランザクションをロールバックする
            userTransaction.rollback();
        }
    }
}
