package com.example.demo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class DemoControllerTest {

    /**
     * テスト対象のクラス
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
    public void init(){
        //@Mockアノテーションのモックオブジェクトを初期化
        //これを実行しないと@Mockアノテーション、@InjectMocksを付与した
        //Mockオブジェクトが利用できない
        MockitoAnnotations.initMocks(this);

        //Mockの設定
        when(demoComponent.getNowDateTime()).thenReturn(getNowDateTimeStr());
        when(demoComponent.getNowDateTimeFinal()).thenReturn(getNowDateTimeStr());
    }

    /**
     * DemoControllerクラスのindexメソッドを確認
     */
    @Test
    public void testDemoController(){
        //Modelオブジェクトを生成
        Model model = DemoControllerTestUtil.getModel();
        //テスト対象クラスのメソッドを実行
        String strPath = demoController.index(model);

        //テスト対象クラスのメソッドで設定されたMapオブジェクトを表示
        System.out.println();
        System.out.println("*** コントローラクラスのindexメソッドで設定されたMapオブジェクト ***");
        System.out.println(model.asMap());
        System.out.println();

        //テスト対象クラスのメソッドの実行結果を確認
        assertEquals("index", strPath);
        Map<String, Object> mapObj = model.asMap();
        assertEquals("2020-07-14T20:54:12", mapObj.get("nowDateTime"));
        assertEquals("2020-07-14T20:54:12", mapObj.get("nowDateTimeFinal"));
    }

    /**
     * 現在時刻を生成し文字列化して返却
     * @return 現在時刻の文字列
     */
    private String getNowDateTimeStr(){
        LocalDateTime nowDateTime = LocalDateTime.of(
                2020, 7, 14, 20, 54, 12);
        return nowDateTime.toString();
    }

}
