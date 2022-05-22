package com.example.demo.mapper.ora;

import com.example.demo.mapper.DemoXlsDataSetLoader;
import com.example.demo.mapper.UserData;
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

import java.util.List;

// JUnit5ベースでMyBatisのテストを実行する
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class
        , DirtiesContextTestExecutionListener.class
        , TransactionDbUnitTestExecutionListener.class
        , DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = DemoXlsDataSetLoader.class
        , databaseConnection = {"dbUnitDatabaseConnectionOra"})
public class UserDataDmlTestOra {

    /**
     * ユーザーデータテーブル(user_data)へアクセスするマッパー
     */
    @Autowired
    private UserDataMapperOra userDataMapperOra;

    /**
     * 各テストメソッドを実行する前に行う処理を定義する.
     */
    @BeforeEach
    public void beforeTest() {
        System.out.println();
        System.out.println("*** UserDataDmlTestOraクラス テスト結果 start ***");
    }

    /**
     * DML(insert,update,delete)文の実行結果を検証する.
     */
    @Test
    @DatabaseSetup("/com/example/demo/xls/databaseSetupTest.xlsx")
    @ExpectedDatabase(value = "/com/example/demo/xls/expectedDatabaseTest.xlsx", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void userDataTest() {
        // ユーザーデータ追加・更新・削除する前のデータを確認
        System.out.println("*** ユーザーデータテーブル(user_data)のデータ DML実行前 ***");
        List<UserData> userDataList = userDataMapperOra.findAll();
        for (UserData userData : userDataList) {
            System.out.println(userData);
        }
        System.out.println();

        // ユーザーデータ(ID=4)を追加
        UserData userData4 = new UserData(4, "テスト　プリン４"
                , 2016, 5, 6, "1", "テスト４", "");
        userDataMapperOra.create(userData4);

        // ユーザーデータ(ID=3)を更新
        UserData userData3 = new UserData(3, "テスト　プリン３更新後"
                , 2015, 4, 21, "2", "テスト３更新後", "");
        userDataMapperOra.update(userData3);

        // ユーザーデータ(ID=2)を削除
        userDataMapperOra.deleteById(2L);

        // ユーザーデータ追加・更新・削除した後のデータを確認
        System.out.println("*** ユーザーデータテーブル(user_data)のデータ DML実行後 ***");
        userDataList = userDataMapperOra.findAll();
        for (UserData userData : userDataList) {
            System.out.println(userData);
        }
    }

    /**
     * 各テストメソッドを実行した後に行う処理を定義する.
     */
    @AfterEach
    public void afterTestClass() {
        System.out.println("*** UserDataDmlTestOraクラス テスト結果 end ***");
        System.out.println();
    }
}
