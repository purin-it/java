package com.example.demo.mapper;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

// JUnit5ベースでMyBatisのテストを実行する
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class
        , DirtiesContextTestExecutionListener.class
        , TransactionDbUnitTestExecutionListener.class
        , DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = DemoXlsDataSetLoader.class
        , databaseConnection = {"dbUnitDatabaseConnection"})
public class UserDataTestJunit5 {

    /**
     * ユーザーデータテーブル(user_data)へアクセスするマッパー
     */
    @Autowired
    private UserDataMapper userDataMapper;

    /**
     * 各テストメソッドを実行する前に行う処理を定義する.
     */
    @BeforeEach
    public void beforeTest() {
        System.out.println();
        System.out.println("*** UserDataTestJunit5クラス テスト結果 start ***");
    }

    /**
     * テストを実行する前に、databaseSetupTest.xlsxに定義したデータを読み込み、
     * 取得したデータを検証する.
     */
    @Test
    @DatabaseSetup("/com/example/demo/xls/databaseSetupTest.xlsx")
    public void userDataTest() {
        System.out.println("*** userDataTestメソッド テスト結果 start ***");

        // @DatabaseSetupアノテーションで設定されたデータを確認
        System.out.println("*** ユーザーデータテーブル(user_data)のデータ ***");
        List<UserData> userDataList = userDataMapper.findAll();
        for (UserData userData : userDataList) {
            System.out.println(userData);
        }
        assertEquals(expectedUserDataList(), userDataList);

        System.out.println("*** userDataTestメソッド テスト結果 end ***");
    }

    /**
     * テストを実行した後で、expectedDatabaseTest.xlsxに定義したデータが
     * 追加されることを検証する.
     */
    @Test
    @DatabaseSetup("/com/example/demo/xls/databaseSetupTest.xlsx")
    @ExpectedDatabase(value="/com/example/demo/xls/expectedDatabaseTest.xlsx"
            , assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void userDataTes2() {
        System.out.println("*** userDataTest2メソッド テスト結果 start ***");

        UserData userDataAdd = new UserData(4, "テスト　プリン４"
                , 2016, 5, 6, "1", "テスト４", "男");
        userDataMapper.create(userDataAdd);

        System.out.println("*** ユーザーデータテーブル(user_data)のデータ ***");
        List<UserData> userDataList = userDataMapper.findAll();
        for (UserData userData : userDataList) {
            System.out.println(userData);
        }

        System.out.println("*** userDataTest2メソッド テスト結果 end ***");
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
    @AfterEach
    public void afterTestClass() {
        System.out.println("*** UserDataTestJunit5クラス テスト結果 end ***");
        System.out.println();
    }
}
