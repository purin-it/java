package com.example.demo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.when;

public class DemoServiceImplTest {

    /**
     * テスト対象のクラス
     * (今回はSpring Bootを利用しないため、Serviceではなく
     *  ServiceImplを対象クラスに指定している)
     */
    @InjectMocks
    private DemoServiceImpl demoServiceImpl;

    /**
     * テスト対象のクラス内で呼ばれるクラスのMockオブジェクト
     */
    @Mock
    private UserDataMapper mapper;

    /**
     * 前処理(各テストケースを実行する前に行われる処理)
     */
    @Before
    public void init() {
        //@Mockアノテーションのモックオブジェクトを初期化
        //これを実行しないと@Mockアノテーション、@InjectMocksを付与した
        //Mockオブジェクトが利用できない
        MockitoAnnotations.initMocks(this);

        //Mockの設定
        //mapper.findAll()メソッドを実行した際の戻り値をここで設定
        when(mapper.findAll()).thenReturn(makeUserDataList());
    }

    /**
     * DemoServiceImplクラスのdemoFormListメソッドの確認
     */
    @Test
    public void testDemoFormList(){
        //テスト対象クラスのメソッドを実行
        List<DemoForm> demoFormList = demoServiceImpl.demoFormList();

        //取得内容をコンソールに表示
        System.out.println("*** demoServiceImpl.demoFormList()の実行結果 ***");
        for(DemoForm demoForm : demoFormList){
            System.out.println(demoForm.toString());
        }
        System.out.println();

        //取得結果の内容を確認
        //assertArrayEqualsは、引数の配列同士が同一かどうかを判定する
        assertArrayEquals(demoFormList.toArray(), expectDemoFormList().toArray());
    }

    /**
     * ユーザーデータリストを生成する
     * @return ユーザーデータリスト
     */
    private List<UserData> makeUserDataList(){
        List<UserData> userDataList = new ArrayList<>();

        //ユーザー1を追加
        userDataList.add(makeUserData(Long.valueOf(1), "テスト　プリン"
                , LocalDate.of(2012, 1, 15), SexEnum.WOMAN));

        //ユーザー2を追加
        userDataList.add(makeUserData(Long.valueOf(2), "テスト　プリン２"
                , LocalDate.of(2013, 3, 19), SexEnum.MAN));

        return userDataList;
    }

    /**
     * ユーザーデータを生成する
     * @param id ID
     * @param name 名前
     * @param birthDay 生年月日
     * @param sexEnum 性別Enum
     * @return ユーザーデータ
     */
    private UserData makeUserData(Long id, String name, LocalDate birthDay, SexEnum sexEnum){
        UserData userData = new UserData();
        if(id != null) {
            userData.setId(id);
        }
        userData.setName(name);
        if(birthDay != null){
            userData.setBirthY(birthDay.getYear());
            userData.setBirthM(birthDay.getMonthValue());
            userData.setBirthD(birthDay.getDayOfMonth());
        }
        if(sexEnum != null){
            userData.setSex(sexEnum.getSex());
            userData.setSex_value(sexEnum.getSex_value());
        }
        return userData;
    }

    /**
     * demoServiceImpl.demoFormList()を実行した際の、想定となる戻り値を生成する
     * @return DemoFormリスト
     */
    private List<DemoForm> expectDemoFormList(){
        List<DemoForm> demoFormList = new ArrayList<>();

        //ユーザー1を追加
        demoFormList.add(makeDemoForm(Long.valueOf(1), "テスト　プリン"
                , LocalDate.of(2012, 1, 15), SexEnum.WOMAN));

        //ユーザー2を追加
        demoFormList.add(makeDemoForm(Long.valueOf(2), "テスト　プリン２"
                , LocalDate.of(2013, 3, 19), SexEnum.MAN));

        return demoFormList;
    }

    /**
     * Demoフォームオブジェクトを生成する
     * @param id ID
     * @param name 名前
     * @param birthDay 生年月日
     * @param sexEnum 性別Enum
     * @return Demoフォームオブジェクト
     */
    private DemoForm makeDemoForm(Long id, String name, LocalDate birthDay, SexEnum sexEnum){
       DemoForm demoForm = new DemoForm();
       if(id != null){
            demoForm.setId(String.valueOf(id));
       }
       demoForm.setName(name);
       if(birthDay != null){
           demoForm.setBirthYear(String.valueOf(birthDay.getYear()));
           demoForm.setBirthMonth(String.valueOf(birthDay.getMonthValue()));
           demoForm.setBirthDay(String.valueOf(birthDay.getDayOfMonth()));
       }
       if(sexEnum != null){
           demoForm.setSex(sexEnum.getSex());
           demoForm.setSex_value(sexEnum.getSex_value());
       }
       return demoForm;
    }
}
