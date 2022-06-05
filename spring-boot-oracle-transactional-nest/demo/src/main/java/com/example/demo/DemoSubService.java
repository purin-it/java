package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DemoSubService {

    /** ユーザーの更新後氏名を定義 */
    /** USER_NAME_OK:13桁39バイトで更新OK、USER_NAME_NG:14桁42バイトで更新NG */
    public static String USER_NAME_OK = "１２３４５６７８９０１２３";
    public static String USER_NAME_NG = "１２３４５６７８９０１２３４";

    /**
     * ユーザーデータテーブル(user_data)へアクセスするマッパー
     */
    @Autowired
    private UserDataMapper userDataMapper;

    /**
     * ユーザーデータ１, ユーザーデータ２を更新するメソッド
     * @param userData1 ユーザーデータ１
     * @param userData2 ユーザーデータ２
     */
    // @Transactionalアノテーションでpropagationの指定がない場合：REQUIRED
    // トランザクションが開始されていなければ新規に開始し、すでに開始されていれば
    // そのトランザクションをそのまま利用する
    @Transactional
    public void transUserDataSub1(UserData userData1, UserData userData2){
        System.out.println("com.example.demo.DemoSubService.transUserDataSub1 start.");

        // 更新前データを表示
        System.out.println("ユーザーデータ１(更新前) : " + userData1.toString());
        System.out.println("ユーザーデータ２(更新前) : " + userData2.toString());

        userData1.setName(USER_NAME_OK);
        userDataMapper.update(userData1);
        userData2.setName(USER_NAME_OK);
        userDataMapper.update(userData2);

        // 更新後データを表示
        System.out.println("ユーザーデータ１(更新後) : " + userData1.toString());
        System.out.println("ユーザーデータ２(更新後) : " + userData2.toString());

        System.out.println("com.example.demo.DemoSubService.transUserDataSub1 end.");
    }

    /**
     * ユーザーデータ３, ユーザーデータ４を更新するメソッド
     * @param userData3 ユーザーデータ３
     * @param userData4 ユーザーデータ４
     */
    @Transactional
    public void transUserDataSub2(UserData userData3, UserData userData4){
        System.out.println("com.example.demo.DemoSubService.transUserDataSub2 start.");

        // 更新前データを表示
        System.out.println("ユーザーデータ３(更新前) : " + userData3.toString());
        System.out.println("ユーザーデータ４(更新前) : " + userData4.toString());

        userData3.setName(USER_NAME_OK);
        userDataMapper.update(userData3);
        userData4.setName(USER_NAME_OK);
        //userData4.setName(USER_NAME_NG);
        userDataMapper.update(userData4);

        // 更新後データを表示
        System.out.println("ユーザーデータ３(更新後) : " + userData3.toString());
        System.out.println("ユーザーデータ４(更新後) : " + userData4.toString());

        System.out.println("com.example.demo.DemoSubService.transUserDataSub2 end.");
    }

    /**
     * ユーザーデータ５, ユーザーデータ６を更新するメソッド
     * @param userData5 ユーザーデータ５
     * @param userData6 ユーザーデータ６
     */
    // @TransactionalアノテーションでpropagationにREQUIRES_NEWを指定した場合
    // 常に新しいトランザクションを開始する
    // トランザクションが存在する場合は中断して新しいトランザクションを開始する
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void transUserDataSub3(UserData userData5, UserData userData6){
        System.out.println("com.example.demo.DemoSubService.transUserDataSub3 start.");

        // 更新前データを表示
        System.out.println("ユーザーデータ５(更新前) : " + userData5.toString());
        System.out.println("ユーザーデータ６(更新前) : " + userData6.toString());

        userData5.setName(USER_NAME_OK);
        userDataMapper.update(userData5);
        userData6.setName(USER_NAME_OK);
        userDataMapper.update(userData6);

        // 更新後データを表示
        System.out.println("ユーザーデータ５(更新後) : " + userData5.toString());
        System.out.println("ユーザーデータ６(更新後) : " + userData6.toString());

        System.out.println("com.example.demo.DemoSubService.transUserDataSub3 end.");
    }

    /**
     * ユーザーデータ７, ユーザーデータ８を更新するメソッド
     * @param userData7 ユーザーデータ７
     * @param userData8 ユーザーデータ８
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void transUserDataSub4(UserData userData7, UserData userData8){
        System.out.println("com.example.demo.DemoSubService.transUserDataSub4 start.");

        // 更新前データを表示
        System.out.println("ユーザーデータ７(更新前) : " + userData7.toString());
        System.out.println("ユーザーデータ８(更新前) : " + userData8.toString());

        userData7.setName(USER_NAME_OK);
        userDataMapper.update(userData7);
        userData8.setName(USER_NAME_OK);
        //userData8.setName(USER_NAME_NG);
        userDataMapper.update(userData8);

        // 更新後データを表示
        System.out.println("ユーザーデータ７(更新後) : " + userData7.toString());
        System.out.println("ユーザーデータ８(更新後) : " + userData8.toString());

        System.out.println("com.example.demo.DemoSubService.transUserDataSub4 end.");
    }
}
