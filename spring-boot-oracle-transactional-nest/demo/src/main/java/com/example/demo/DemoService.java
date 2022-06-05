package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DemoService {

    /**
     * ユーザーデータテーブル(user_data)へアクセスするマッパー
     */
    @Autowired
    private UserDataMapper userDataMapper;

    /**
     * ユーザーデータテーブルの更新を行うサービス
     */
    @Autowired
    private DemoSubService demoSubService;

    /**
     * ユーザーデータテーブル(user_data)を更新するトランザクション
     */
    @Transactional
    public void transUserData() {
        System.out.println("com.example.demo.DemoService.transUserData start.");
        System.out.println();

        // id=1～10までをもつユーザーデータリストを取得する
        List<Long> idList = new ArrayList<>();
        for(long i = 1; i == 10; i++){
            idList.add(i);
        }
        List<UserData> userDataList = userDataMapper.findByIdList(idList);

        // ユーザーデータリストのレコード数が10件でなければ処理を終了する
        if(userDataList == null || userDataList.size() != 10){
            return;
        }

        // ユーザーデータ１、ユーザーデータ２を更新する(@Transactionalアノテーションで
        // propagationの指定がない場合)のメソッドを呼びだし
        demoSubService.transUserDataSub1(userDataList.get(0), userDataList.get(1));

        // ユーザーデータ３、ユーザーデータ４を更新する(@Transactionalアノテーションで
        // propagationの指定がない場合)のメソッドを呼びだし
        // エラーを発生させる場合もある
        try{
            demoSubService.transUserDataSub2(userDataList.get(2), userDataList.get(3));
        }catch(Exception ex){
            System.err.println(ex);
        }

        // ユーザーデータ５、ユーザーデータ６を更新する(@Transactionalアノテーションで
        // propagationにREQUIRES_NEWを指定した場合)のメソッドを呼びだし
        demoSubService.transUserDataSub3(userDataList.get(4), userDataList.get(5));

        // ユーザーデータ７、ユーザーデータ８を更新する(@Transactionalアノテーションで
        // propagationにREQUIRES_NEWを指定した場合)のメソッドを呼びだし
        // エラーを発生させる場合もある
        try{
            demoSubService.transUserDataSub4(userDataList.get(6), userDataList.get(7));
        }catch(Exception ex){
            System.err.println(ex);
        }

        // ユーザーデータ９、ユーザーデータ１０を更新する
        // エラーを発生させる場合もある
        // 更新前データを表示
        System.out.println("ユーザーデータ９(更新前) : " + userDataList.get(8).toString());
        System.out.println("ユーザーデータ１０(更新前) : " + userDataList.get(9).toString());

        userDataList.get(8).setName(DemoSubService.USER_NAME_OK);
        userDataMapper.update(userDataList.get(8));
        userDataList.get(9).setName(DemoSubService.USER_NAME_OK);
        //userDataList.get(9).setName(DemoSubService.USER_NAME_NG);
        userDataMapper.update(userDataList.get(9));

        // 更新後データを表示
        System.out.println("ユーザーデータ９(更新後) : " + userDataList.get(8).toString());
        System.out.println("ユーザーデータ１０(更新後) : " + userDataList.get(9).toString());

        System.out.println("com.example.demo.DemoService.transUserData end.");
    }

}
