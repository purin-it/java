package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class DemoService {

    /**
     * ユーザーデータテーブル(user_data)へアクセスするマッパー
     */
    @Autowired
    private UserDataMapper userDataMapper;

    /**
     * ユーザーデータテーブル(user_data)を更新するトランザクション
     */
    public void verifyPerformance() {
        System.out.println("com.example.demo.DemoService.verifyPerformance start.");
        System.out.println();

        // 初回DB接続時は時間がかかるため、あえて性能測定対象外のSQLを1度実行しておく
        userDataMapper.findById(Long.valueOf(1));
        System.out.println();

        System.out.println("--- ユーザーデータテーブル(user_data)から生年月日が基準日前後10日間の"
                + "データを、BETWEEN句を使わないで全件取得するSQLを実行します start. ---");

        // 処理前の現在時刻を取得
        long startTime = System.currentTimeMillis();

        // 生年月日が基準日(2012/1/20)前後10日間のデータを、BETWEEN句を使わないで全件取得する
        Calendar cal = Calendar.getInstance();
        cal.set(2012, 0, 20);
        List<UserData> userDataList = userDataMapper.findByBirthdayNotBetween(cal.getTime());

        // 処理後の現在時刻を取得
        long endTime = System.currentTimeMillis();

        System.out.println("取得件数 : " + userDataList.size());
        System.out.println("処理時間：" + (endTime - startTime) + " ms");

        System.out.println("--- ユーザーデータテーブル(user_data)から生年月日が基準日前後10日間の"
                + "データを、BETWEEN句を使わないで全件取得するSQLを実行します end. ---");
        System.out.println();

        System.out.println("--- ユーザーデータテーブル(user_data)から生年月日が基準日前後10日間の"
                + "データを、BETWEEN句を使って全件取得するSQLを実行します start. ---");

        // 処理前の現在時刻を取得
        startTime = System.currentTimeMillis();

        // 生年月日が基準日(2012/1/20)前後10日間のデータを、BETWEEN句を使って全件取得する
        userDataList = userDataMapper.findByBirthdayBetween(cal.getTime());

        // 処理後の現在時刻を取得
        endTime = System.currentTimeMillis();

        System.out.println("取得件数 : " + userDataList.size());
        System.out.println("処理時間：" + (endTime - startTime) + " ms");

        System.out.println("--- ユーザーデータテーブル(user_data)から生年月日が基準日前後10日間の"
                + "データを、BETWEEN句を使って全件取得するSQLを実行します end. ---");

        System.out.println("com.example.demo.DemoService.verifyPerformance end.");
    }

}
