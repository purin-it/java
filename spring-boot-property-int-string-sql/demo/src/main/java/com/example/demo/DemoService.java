package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoService {

    // プロパティファイルからtest.string.prop1の値を取得し設定
    // 設定値：5となる
    @Value("${test.string.prop1}")
    private String prop1;

    // プロパティファイルからtest.string.prop2の値を取得し設定
    // 設定値：空文字となる
    @Value("${test.string.prop2}")
    private String prop2;

    // プロパティファイルに存在しないtest.string.prop3の値を設定
    // 設定値：0となる
    @Value("${test.string.prop3:0}")
    private String prop3;

    @Autowired
    private UserDataMapper userDataMapper;

    /**
     * 生年月日を検索条件にユーザーデータテーブル(user_data)のデータを取得する
     */
    public void getUserDataByBirthday(){
        System.out.println("prop1の値 : " + prop1);
        System.out.println("prop2の値 : " + prop2);
        System.out.println("prop2は空文字か? : " + "".equals(prop2));
        System.out.println("prop3の値 : " + prop3);

        List<UserData> userDataList = userDataMapper.findByBirthdayPropStr(prop1);
        System.out.println();
        System.out.println("*** prop1の値を利用して検索した結果 ***");
        for(UserData userData : userDataList){
            System.out.println(userData);
        }
        System.out.println();

        List<UserData> userDataList2 = userDataMapper.findByBirthdayPropStr(prop2);
        System.out.println("*** prop2の値を利用して検索した結果 ***");
        for(UserData userData : userDataList2){
            System.out.println(userData);
        }
        System.out.println();

        List<UserData> userDataList3 = userDataMapper.findByBirthdayPropStr(prop3);
        System.out.println("*** prop3の値を利用して検索した結果 ***");
        for(UserData userData : userDataList3){
            System.out.println(userData);
        }
        System.out.println();
    }

}
