package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DemoService {

    /** 2回目の更新後氏名を定義 */
    /** USER_NAME_OK:13桁39バイトで更新OK、USER_NAME_NG:14桁42バイトで更新NG */
    private static String USER_NAME_OK = "１２３４５６７８９０１２３";
    private static String USER_NAME_NG = "１２３４５６７８９０１２３４";

    /**
     * ユーザーデータテーブル(user_data)へアクセスするマッパー
     */
    @Autowired
    private UserDataMapper userDataMapper;

    /**
     * ユーザーデータテーブル(user_data)を更新するトランザクション
     */
    // @Transactionalアノテーションで、このメソッド内のDB更新が全て成功すれば
    // コミットされ、そうでなければロールバックされる
    @Transactional
    public void transUserData() {
        UserData userData = userDataMapper.findById(Long.valueOf(1));

        // ユーザーデータが取得できなければ処理を終了する
        if (userData == null) {
            System.out.println("id=1のユーザーデータは見つかりませんでした。");
            return;
        }

        // 更新前データを表示
        System.out.println("ユーザーデータ(更新前) : " + userData.toString());

        // 氏名を更新する
        userData.setName("テスト　プリン１　更新後");
        userDataMapper.update(userData);

        // 更新後データを表示
        userData = userDataMapper.findById(Long.valueOf(1));
        System.out.println("ユーザーデータ(1回目更新後) : " + userData.toString());

        // 氏名を再度更新する
        userData.setName(USER_NAME_OK);
        userDataMapper.update(userData);

        // 更新後データを表示
        userData = userDataMapper.findById(Long.valueOf(1));
        System.out.println("ユーザーデータ(2回目更新後) : " + userData.toString());
    }

}
