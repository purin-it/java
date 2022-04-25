package com.example.demo.mapper;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

// JUnit4ベースでMyBatisのテストを実行する
@RunWith(SpringRunner.class)
@MybatisTest
// デフォルトのDBでなく、実際のDBを利用する
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// テストを実行する際のリスナーを定義する
// DependencyInjectionTestExecutionListenerとDirtiesContextTestExecutionListenerで、
// SpringのDI機能を利用できるようにし、TransactionDbUnitTestExecutionListenerと
// DbUnitTestExecutionListenerで、トランザクション管理やDBデータの設定・検証・後処理が
// できるようにしている
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class
        , DirtiesContextTestExecutionListener.class
        , TransactionDbUnitTestExecutionListener.class
        , DbUnitTestExecutionListener.class})
// ExcelのデータをDBに設定する際、DemoXlsDataSetLoaderクラスを利用し、
// DB接続する際、(DemoTestDbConfigクラスの)dbUnitDatabaseConnectionという
// データソースコネクションファクトリを利用する
@DbUnitConfiguration(dataSetLoader = DemoXlsDataSetLoader.class
        , databaseConnection = {"dbUnitDatabaseConnection"})
public class UserDataTest1 {

    /**
     * ユーザーデータテーブル(user_data)へアクセスするマッパー
     */
    @Autowired
    private UserDataMapper userDataMapper;

    /**
     * 各テストメソッドを実行する前に行う処理を定義する.
     */
    @Before
    public void beforeTest() {
        System.out.println();
        System.out.println("*** UserDataTest1クラス テスト結果 start ***");
    }

    /**
     * テストを実行する前に、databaseSetupTest.xlsxに定義したデータを読み込み、
     * 取得したデータを検証する.
     */
    @Test
    @DatabaseSetup("/com/example/demo/xls/databaseSetupTest.xlsx")
    public void userDataTest() {
        // @DatabaseSetupアノテーションで設定されたデータを確認
        System.out.println("*** ユーザーデータテーブル(user_data)のデータ ***");
        List<UserData> userDataList = userDataMapper.findAll();
        for (UserData userData : userDataList) {
            System.out.println(userData);
        }
        assertEquals(expectedUserDataList(), userDataList);
    }

    /**
     * 予想されるテスト結果のデータを定義する.
     * @return 予想されるテスト結果のデータ
     */
    private List<UserData> expectedUserDataList() {
        List<UserData> userDataList = new ArrayList<>();
        UserData userData1 = new UserData(1, "テスト　プリン１"
                , 2012, 2, 10, "1", null, "男");
        UserData userData2 = new UserData(2, "テスト　プリン２"
                , 2013, 3, 15, "2", null, "女");
        UserData userData3 = new UserData(3, "テスト　プリン３"
                , 2015, 4, 21, "2", "テスト３", "女");
        userDataList.add(userData1);
        userDataList.add(userData2);
        userDataList.add(userData3);
        return userDataList;
    }

    /**
     * 各テストメソッドを実行した後に行う処理を定義する.
     */
    @After
    public void afterTestClass() {
        System.out.println("*** UserDataTest1クラス テスト結果 end ***");
        System.out.println();
    }
}
