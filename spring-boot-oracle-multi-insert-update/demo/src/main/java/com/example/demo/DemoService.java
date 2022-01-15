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
     * ユーザーデータテーブル(user_data)のデータをまとめて追加する.
     */
    @Transactional
    public void insertMultiData() {
        // 更新前のユーザーデータテーブルのデータを出力する
        List<UserData> userDataListReg = userDataMapper.findAll();
        System.out.println("更新前データ : ");
        printUserDataList(userDataListReg);

        // ユーザーデータテーブルに複数レコードをまとめて追加する.
        List<UserData> userDataList = new ArrayList<>();
        userDataList.add(makeUserData(1));
        userDataList.add(makeUserData(2));
        userDataList.add(makeUserData(3));
        userDataMapper.insertMulti(userDataList);

        // 更新後のユーザーデータテーブルのデータを出力する
        List<UserData> userDataListUpd = userDataMapper.findAll();
        System.out.println("更新後データ : ");
        printUserDataList(userDataListUpd);
    }

    /**
     * ユーザーデータテーブル(user_data)のデータをまとめて更新する.
     */
    @Transactional
    public void updateMultiData() {
        // 更新前のユーザーデータテーブルのデータを出力する
        List<UserData> userDataListReg = userDataMapper.findAll();
        System.out.println("更新前データ : ");
        printUserDataList(userDataListReg);

        // ユーザーデータテーブルの複数レコードをまとめて更新する.
        for (UserData userData : userDataListReg) {
            makeUserDataUpd(userData);
        }
        userDataMapper.updateMulti(userDataListReg);

        // 更新後のユーザーデータテーブルのデータを出力する
        List<UserData> userDataListUpd = userDataMapper.findAll();
        System.out.println("更新後データ : ");
        printUserDataList(userDataListUpd);
    }

    /**
     * 引数のIDをもつユーザーデータを生成する.
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

    /**
     * 引数のユーザーデータの名前・メモを更新する.
     * @param userData ユーザーデータ
     */
    private void makeUserDataUpd(UserData userData) {
        userData.setName(userData.getName() + "　更新後");
        userData.setMemo(userData.getMemo() + "　更新後");
    }

    /**
     * 引数のユーザーデータリストの中身を出力する.
     * @param userDataList ユーザーデータリスト
     */
    private void printUserDataList(List<UserData> userDataList) {
        if (userDataList == null || userDataList.size() == 0) {
            System.out.println("ユーザーデータはありません。");
        } else {
            for (UserData userData : userDataList) {
                System.out.println(userData);
            }
        }
        System.out.println();
    }
}
