package com.example.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.ui.Model;

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

    @Mock
    private DemoComponent demoComponent;

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
        PowerMockito.doReturn("test1")
                .when(DemoUtil.class, "getTextString", Mockito.any());

        //Mockオブジェクト呼出時の戻り値を設定(DemoComponent.java)
        Mockito.doReturn("test2")
                .when(demoComponent).getTextString(Mockito.any());

        //modelオブジェクトを取得し、テスト対象クラスのメソッドを実行
        Model model = DemoControllerTestUtil.getModel();
        String returnVal = demoController.index(model);

        //戻り値が"index"であることを確認
        assertEquals("index", returnVal);

        //modelオブジェクトの設定値を確認
        //demoComponent.getTextStringが呼ばれた場合は設定したMockの戻り値が設定され、
        //エラーメッセージが設定されないことを確認
        Map<String, Object> modelValue = model.asMap();
        assertEquals("test1", modelValue.get("textString1"));
        assertEquals("test2", modelValue.get("textString2"));
        assertNull(modelValue.get("errMsg"));
    }

    @Test
    public void testDemoControllerDemoUtilException() throws Exception {
        //Mockオブジェクト呼出時の例外を設定(DemoUtil.java)
        //doThrow句内に例外を設定するが、発生し得ない例外(例：Exception)は設定できない
        PowerMockito.doThrow(new IOException())
                .when(DemoUtil.class, "getTextString", Mockito.any());

        //Mockオブジェクト呼出時の戻り値を設定(DemoComponent.java)
        Mockito.doReturn("test2")
                .when(demoComponent).getTextString(Mockito.any());

        //modelオブジェクトを取得し、テスト対象クラスのメソッドを実行
        Model model = DemoControllerTestUtil.getModel();
        String returnVal = demoController.index(model);

        //戻り値が"error"であることを確認
        assertEquals("error", returnVal);

        //modelオブジェクトの設定値を確認
        //IOExceptionが発生した場合のエラーメッセージ「入出力例外が発生しました」が
        //設定され、textString1・textString2が設定されないことを確認
        Map<String, Object> modelValue = model.asMap();
        assertEquals("入出力例外が発生しました", modelValue.get("errMsg"));
        assertNull(modelValue.get("textString1"));
        assertNull(modelValue.get("textString2"));
    }

    @Test
    public void testDemoControllerDemoComponentException() throws Exception {
        //Mockオブジェクト呼出時の戻り値を設定(DemoUtil.java)
        PowerMockito.doReturn("test1")
                .when(DemoUtil.class, "getTextString", Mockito.any());

        //Mockオブジェクト呼出時の例外を設定(DemoComponent.java)
        Mockito.doThrow(new IOException())
                .when(demoComponent).getTextString(Mockito.any());

        //modelオブジェクトを取得し、テスト対象クラスのメソッドを実行
        Model model = DemoControllerTestUtil.getModel();
        String returnVal = demoController.index(model);

        //戻り値が"error"であることを確認
        assertEquals("error", returnVal);

        //modelオブジェクトの設定値を確認
        //IOExceptionが発生した場合のエラーメッセージ「入出力例外が発生しました」が
        //設定され、textString1・textString2が設定されないことを確認
        Map<String, Object> modelValue = model.asMap();
        assertEquals("入出力例外が発生しました", modelValue.get("errMsg"));
        assertNull(modelValue.get("textString1"));
        assertNull(modelValue.get("textString2"));
    }
}
