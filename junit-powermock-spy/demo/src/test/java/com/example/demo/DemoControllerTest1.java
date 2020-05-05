package com.example.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import static org.junit.Assert.assertEquals;

import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.ui.Model;

import java.util.Map;

//staticメソッドをMock化するにはPowerMockを利用
//@PrepareForTestアノテーションで、staticメソッドを含むクラスを指定
@RunWith(PowerMockRunner.class)
@PrepareForTest({DemoUtil.class})
public class DemoControllerTest1 {

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

        //DemoUtilクラスをSpyで一部をMock設定
        PowerMockito.spy(DemoUtil.class);

        //Mockオブジェクト呼出時の値を設定
        //DemoUtil.getRealString1が呼ばれた場合のみMock設定し、
        //DemoUtil.getRealString2が呼ばれた場合はMock設定しない
        PowerMockito.doReturn("mockString1").when(DemoUtil.class);
        DemoUtil.getRealString1();

        //voidメソッドが呼ばれた場合は何もしないようにするには、下記のMock設定が必要
        PowerMockito.doNothing().when(DemoUtil.class);
        DemoUtil.testVoid();
    }

    /**
     * DemoControllerクラスのindexメソッドの確認
     */
    @Test
    public void testDemoController(){
        //modelオブジェクトを取得し、テスト対象クラスのメソッドを実行
        Model model = DemoControllerTestUtil.getModel();
        String returnVal = demoController.index(model);

        //戻り値が"index"であることを確認
        assertEquals("index", returnVal);

        //modelオブジェクトの設定値を確認
        //DemoUtil.getRealString1が呼ばれた場合は設定したMockの戻り値が設定され、
        //DemoUtil.getRealString2が呼ばれた場合は実際の戻り値が設定される
        Map<String, Object> modelValue = model.asMap();
        assertEquals("mockString1", modelValue.get("realString1"));
        assertEquals("realString2", modelValue.get("realString2"));

        //modelオブジェクトの設定値を出力
        System.out.println("realString1の値 : " + modelValue.get("realString1")
                        + ", realString2の値 : " + modelValue.get("realString2"));
    }
}
