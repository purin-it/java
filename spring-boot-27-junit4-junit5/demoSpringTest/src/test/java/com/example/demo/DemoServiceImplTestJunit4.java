package com.example.demo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;

// JUnit4のアサーションを指定
import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)  // JUnit4で実行
public class DemoServiceImplTestJunit4 {

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
     * 初期化したMockオブジェクトを保持するクラス
     */
    private AutoCloseable closeable;

    /**
     * 前処理(各テストケースを実行する前に行われる処理)
     */
    @Before  // JUnit4のアノテーション
    public void init() {
    	/* @Mockアノテーションのモックオブジェクトを初期化
    	 * これを実行しないと@Mockアノテーション、@InjectMocksを付与した
    	 * Mockオブジェクトが利用できない
    	 * MockitoAnnotations.initMocksメソッドは非推奨となったため
    	 * 代わりにopenMocksメソッドを利用 */
    	closeable = MockitoAnnotations.openMocks(this);

        // Mockの設定
        // mapper.findMaxId()メソッドを実行した際の戻り値をここで設定
    	when(mapper.findMaxId()).thenReturn(2L);
    }

    /**
     * DemoServiceImplクラスのcreateOrUpdateForAddメソッド(追加時)の確認
     */
    @Test  // JUnit4のアノテーション
    public void testCreateOrUpdateForAdd(){
        // 追加処理を行う場合の、テスト対象メソッドの引数を生成
        DemoForm demoFormAdd = makeDemoForm(null, "テスト　プリン３"
                , LocalDate.of(2014, 4, 20), SexEnum.MAN);

        // テスト対象メソッドの実行
        demoServiceImpl.createOrUpdate(demoFormAdd);
        System.out.println("*** demoServiceImpl.createOrUpdateForAdd"
                + "(DemoForm(追加用))の実行結果 ***");

        // テスト対象メソッドを実行した結果、mapper.findMaxId()が1回呼ばれたことを確認
        verify(mapper, times(1)).findMaxId();
        System.out.println("mapper.findMaxId()は1回呼ばれました");

        // テスト対象メソッドを実行した結果、mapper.create(UserData)が1回呼ばれたことを確認
        ArgumentCaptor<UserData> userDataCaptor
                = ArgumentCaptor.forClass(UserData.class);
        verify(mapper, times(1))
                .create(userDataCaptor.capture());
        System.out.println("mapper.create(UserData)は1回呼ばれました");

        // mapper.create(UserData)を呼び出した際の引数が想定通りであることを確認
        List<UserData> listUserData = userDataCaptor.getAllValues();
        assertEquals(1, listUserData.size());
        UserData expectUserData = makeUserData(3L, "テスト　プリン３"
                , LocalDate.of(2014, 4, 20), SexEnum.MAN);
        assertEquals(expectUserData.toString(), listUserData.get(0).toString());
        System.out.println("mapper.create(UserData)の引数 ： "
                + listUserData.get(0).toString());

        // テスト対象メソッドを実行した結果、mapper.update(UserData)は呼ばれないことを確認
        // any()は任意の引数を表す
        verify(mapper, times(0)).update(any());
        System.out.println("mapper.update(UserData)は呼ばれませんでした");
        System.out.println();
    }

    /**
     * DemoServiceImplクラスのcreateOrUpdateForAddメソッド(更新時)の確認
     */
    @Test  // JUnit4のアノテーション
    public void testCreateOrUpdateForUpdate(){
        // 更新処理を行う場合の、テスト対象メソッドの引数を生成
        DemoForm demoFormUpd = makeDemoForm(2L, "テスト　プリン２"
                , LocalDate.of(2013, 3, 19), SexEnum.WOMAN);

        // テスト対象メソッドの実行
        demoServiceImpl.createOrUpdate(demoFormUpd);
        System.out.println("*** demoServiceImpl.createOrUpdateForAdd"
                + "(DemoForm(更新用))の実行結果 ***");

        // テスト対象メソッドを実行した結果、mapper.findMaxId()が呼ばれないことを確認
        verify(mapper, times(0)).findMaxId();
        System.out.println("mapper.findMaxId()は呼ばれませんでした");

        // テスト対象メソッドを実行した結果、mapper.create(UserData)が呼ばれないことを確認
        verify(mapper, times(0)).create(any());
        System.out.println("mapper.create(UserData)は呼ばれませんでした");

        // テスト対象メソッドを実行した結果、mapper.update(UserData)が1回呼ばれたことを確認
        ArgumentCaptor<UserData> userDataCaptor
                = ArgumentCaptor.forClass(UserData.class);
        verify(mapper, times(1))
                .update(userDataCaptor.capture());
        System.out.println("mapper.update(UserData)は1回呼ばれました");

        // mapper.update(UserData)を呼び出した際の引数が想定通りであることを確認
        List<UserData> listUserData = userDataCaptor.getAllValues();
        assertEquals(1, listUserData.size());
        UserData expectUserData = makeUserData(2L, "テスト　プリン２"
                , LocalDate.of(2013, 3, 19), SexEnum.WOMAN);
        assertEquals(expectUserData.toString(), listUserData.get(0).toString());
        System.out.println("mapper.update(UserData)の引数 ： "
                + listUserData.get(0).toString());
        System.out.println();
    }
    
    /**
     * 後処理(各テストケースを実行した前に行われる処理)
     * @throws Exception 何らかの例外
     */
    @After  // JUnit4のアノテーション
    public void terminate() throws Exception {
    	// Mockオブジェクトのリソースを解放
    	closeable.close();
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
        if(id != null){
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
