package com.example.demo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public class DemoControllerTest {

    /**
     * テスト対象クラス
     */
    @InjectMocks
    private DemoController demoController;

    /**
     * テスト対象のクラス内で呼ばれるクラスのMockオブジェクト
     */
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
    }

    /**
     * DemoControllerクラスのindexメソッドの確認
     */
    @Test
    public void testDemoControllerNormal() throws IOException {
        //demoComponent.getTextStringメソッドでIOExceptionが発生し得るので
        //メソッドにthrows句を付与している

        //Mockオブジェクト呼出時の値を設定
        doReturn("abcdeテスト").when(demoComponent).getTextString();

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
    public void testDemoControllerNotFoundException() throws IOException {
        //Mockオブジェクト呼出時の例外を設定
        //doThrow句内に例外を設定するが、発生し得ない例外(例：Exception)は設定できない
        doThrow(new FileNotFoundException()).when(demoComponent).getTextString();

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
    public void testDemoControllerIOException() throws IOException {
        //Mockオブジェクト呼出時の値を設定
        //doThrow句内に例外を設定するが、発生し得ない例外(例：Exception)は設定できない
        doThrow(new IOException()).when(demoComponent).getTextString();

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
