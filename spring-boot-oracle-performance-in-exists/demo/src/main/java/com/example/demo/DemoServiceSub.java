package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
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

        // 検索時に利用する基準日(2012/1/20)を生成する
        Calendar basicDate = Calendar.getInstance();
        basicDate.set(2012, 0, 20, 0, 0, 0);
        basicDate.set(Calendar.MILLISECOND, 0);

        System.out.println("--- IN句を使って、ユーザーデータテーブル(user_data)の生年月日が"
                + "基準日と一致するデータを全件取得するSQLを実行します start. ---");

        // 処理前の現在時刻を取得
        long startTime = System.currentTimeMillis();

        // IN句を使って、ユーザーデータテーブル(user_data)の生年月日が基準日と
        // 一致するデータを全件取得する
        List<UserData> userDataList = userDataMapper.findByBirthdayIn(basicDate.getTime());

        // 処理後の現在時刻を取得
        long endTime = System.currentTimeMillis();

        System.out.println("取得件数 : " + userDataList.size());
        System.out.println("処理時間：" + (endTime - startTime) + " ms");
        System.out.println("--- IN句を使って、ユーザーデータテーブル(user_data)の生年月日が"
                + "基準日と一致するデータを全件取得するSQLを実行します end. ---");
        System.out.println();

        System.out.println("--- EXISTS句を使って、ユーザーデータテーブル(user_data)の生年月日が"
                + "基準日と一致するデータを全件取得するSQLを実行します start. ---");

        // 処理前の現在時刻を取得
        startTime = System.currentTimeMillis();

        // EXISTS句を使って、ユーザーデータテーブル(user_data)の生年月日が基準日と
        // 一致するデータを全件取得する
        userDataList = userDataMapper.findByBirthdayExists(basicDate.getTime());

        // 処理後の現在時刻を取得
        endTime = System.currentTimeMillis();

        System.out.println("取得件数 : " + userDataList.size());
        System.out.println("処理時間：" + (endTime - startTime) + " ms");
        System.out.println("--- EXISTS句を使って、ユーザーデータテーブル(user_data)の生年月日が"
                + "基準日と一致するデータを全件取得するSQLを実行します end. ---");
        System.out.println();
    }
}
