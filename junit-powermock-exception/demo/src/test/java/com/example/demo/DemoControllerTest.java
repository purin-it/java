package com.example.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.ui.Model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

//staticメソッドをMock化するにはPowerMockを利用
//@PrepareForTestアノテーションで、staticメソッドを含むクラスを指定
@RunWith(PowerMockRunner.class)
@PrepareForTest({DemoUtil.class})
public class DemoControllerTest {

    /**
     * テスト対象クラス
     */
    @InjectMocks
    private DemoController demoController;

    /**
     * 前処理(各テストケースを実行する前に行われる処理)
     */
    @Before
    public void init() {
        //@Mockアノテーションのモックオブジェクトを初期化
        //これを実行しないと@Mockアノテーション、@InjectMocksを付与した
        //Mockオブジェクトが利用できない
        MockitoAnnotations.initMocks(this);

        //DemoUtilクラスをMock化
        PowerMockito.mockStatic(DemoUtil.class);
    }

    /**
     * DemoControllerクラスのindexメソッドの確認
     */
    @Test
    public void testDemoControllerNormal() throws Exception {
        //Mockオブジェクト呼出時の戻り値を設定(DemoUtil.java)
        //when句に(staticメソッドをもつ)クラス名・staticメソッド名・引数を順に指定するが
        //例外が発生し得るため、「throws Exception」を付与する
        PowerMockito.doReturn("abcdeテスト")
                .when(DemoUtil.class, "getTextString");

        //modelオブジェクトを取得し、テスト対象クラスのメソッドを実行
        Model model = DemoControllerTestUtil.getModel();
        String returnVal = demoController.index(model);

        //戻り値が"index"であることを確認
        assertEquals("index", returnVal);

        //modelオブジェクトの設定値を確認
        //demoComponent.getTextStringが呼ばれた場合は設定したMockの戻り値が設定され、
        //エラーメッセージが設定されないことを確認
        Map<String, Object> modelValue = model.asMap();
        assertEquals("abcdeテスト", modelValue.get("textString"));
        assertNull(modelValue.get("errMsg"));
    }

    @Test
    public void testDemoControllerNotFoundException() throws Exception {
        //Mockオブジェクト呼出時の例外を設定(DemoUtil.java)
        //doThrow句内に例外を設定するが、発生し得ない例外(例：Exception)は設定できない
        PowerMockito.doThrow(new FileNotFoundException())
                .when(DemoUtil.class, "getTextString");

        //modelオブジェクトを取得し、テスト対象クラスのメソッドを実行
        Model model = DemoControllerTestUtil.getModel();
        String returnVal = demoController.index(model);

        //戻り値が"error"であることを確認
        assertEquals("error", returnVal);

        //modelオブジェクトの設定値を確認
        //FileNotFoundExceptionが発生した場合のエラーメッセージ「ファイルが見つかりませんでした」が
        //設定され、textStringが設定されないことを確認
        Map<String, Object> modelValue = model.asMap();
        assertEquals("ファイルが見つかりませんでした", modelValue.get("errMsg"));
        assertNull(modelValue.get("textString"));
    }

    @Test
    public void testDemoControllerIOException() throws Exception {
        //Mockオブジェクト呼出時の例外を設定(DemoUtil.java)
        //doThrow句内に例外を設定するが、発生し得ない例外(例：Exception)は設定できない
        PowerMockito.doThrow(new IOException())
                .when(DemoUtil.class, "getTextString");

        //modelオブジェクトを取得し、テスト対象クラスのメソッドを実行
        Model model = DemoControllerTestUtil.getModel();
        String returnVal = demoController.index(model);

        //戻り値が"error"であることを確認
        assertEquals("error", returnVal);

        //modelオブジェクトの設定値を確認
        //IOExceptionが発生した場合のエラーメッセージ「入出力例外が発生しました」が
        //設定され、textStringが設定されないことを確認
        Map<String, Object> modelValue = model.asMap();
        assertEquals("入出力例外が発生しました", modelValue.get("errMsg"));
        assertNull(modelValue.get("textString"));
    }
}
