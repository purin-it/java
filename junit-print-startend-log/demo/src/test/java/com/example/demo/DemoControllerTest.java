package com.example.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.ui.Model;
import java.time.LocalDateTime;
import java.util.Map;
import static org.junit.Assert.assertEquals;

//demoComponentクラスのインスタンス生成処理をMock化するためPowerMockを利用し、
//@PrepareForTestアノテーションで、テスト対象クラスとMockクラスを指定
@RunWith(PowerMockRunner.class)
@PrepareForTest({DemoController.class, DemoComponent.class})
public class DemoControllerTest {

    /**
     * テスト対象のクラス
     * (インスタンス生成をinitメソッドで実行するため、アノテーションは付与していない)
     */
    private DemoController demoController;

    /**
     * テスト対象のクラス内で呼ばれるクラスのMockオブジェクト
     * (インスタンス生成をinitメソッドで実行するため、アノテーションは付与していない)
     */
    private DemoComponent demoComponent;

    /**
     * クラス名
     * (initメソッド内で設定する)
     */
    private String classPath;

    /**
     * 前処理(各テストケースを実行する前に行われる処理)
     */
    @Before
    public void init() throws Exception {
        //テスト対象クラスで呼ばれるクラスとそのインスタンス生成処理をMock化
        //finalメソッドをMock化できるようにするには、PowerMockを利用する必要がある
        demoComponent = PowerMockito.mock(DemoComponent.class);
        PowerMockito.whenNew(DemoComponent.class).withNoArguments().thenReturn(demoComponent);

        //テスト対象クラスのインスタンスを生成
        demoController = new DemoController();

        //Mockの設定
        Mockito.when(demoComponent.getNowDateTime()).thenReturn(getNowDateTimeStr());
        Mockito.when(demoComponent.getNowDateTimeFinal()).thenReturn(getNowDateTimeStr());

        //クラスパスをフルパスで設定
        classPath = this.getClass().getName();
    }

    /**
     * DemoControllerクラスのindexメソッドを確認
     */
    @Test
    public void testDemoController(){
        //メソッド名を取得し、開始ログを出力
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        printStartLog(methodName);

        //Modelオブジェクトを生成
        Model model = DemoControllerTestUtil.getModel();
        //テスト対象クラスのメソッドを実行
        String strPath = demoController.index(model);

        //テスト対象クラスのメソッドで設定されたMapオブジェクトを表示
        System.out.println("*** コントローラクラスのindexメソッドで設定されたMapオブジェクト ***");
        System.out.println(model.asMap());

        //テスト対象クラスのメソッドの実行結果を確認
        assertEquals("index", strPath);
        Map<String, Object> mapObj = model.asMap();
        assertEquals("2020-07-14T20:54:12", mapObj.get("nowDateTime"));
        assertEquals("2020-07-14T20:54:12", mapObj.get("nowDateTimeFinal"));

        //終了ログを出力
        printEndLog(methodName);
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

    /**
     * 開始ログを出力
     * @param methodName メソッド名
     */
    private void printStartLog(String methodName){
        System.out.println();
        System.out.println("===== " + classPath + "クラス " + methodName + "メソッド 開始 =====");
    }

    /**
     * 終了ログを出力
     * @param methodName メソッド名
     */
    private void printEndLog(String methodName){
        System.out.println("===== " + classPath + "クラス " + methodName + "メソッド 終了 =====");
        System.out.println();
    }
}
