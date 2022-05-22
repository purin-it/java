package com.example.demo.mapper.ms;

import com.example.demo.mapper.DemoXlsDataSetLoader;
import com.example.demo.mapper.UserData;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
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

import static org.junit.Assert.assertEquals;

// JUnit5ベースでMyBatisのテストを実行する
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class
        , DirtiesContextTestExecutionListener.class
        , TransactionDbUnitTestExecutionListener.class
        , DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = DemoXlsDataSetLoader.class
        , databaseConnection = {"dbUnitDatabaseConnectionMs"})
public class UserDataTruncateTestMs {

    /**
     * ユーザーデータテーブル(user_data)へアクセスするマッパー
     */
    @Autowired
    private UserDataMapperMs userDataMapperMs;

    /**
     * 各テストメソッドを実行する前に行う処理を定義する.
     */
    @BeforeEach
    public void beforeTest() {
        System.out.println();
        System.out.println("*** UserDataTruncateTestMsクラス テスト結果 start ***");
    }

    /**
     * truncate文の実行結果を検証する.
     */
    @Test
    public void userDataTest() {
        // ユーザーデータ削除する前のデータを確認
        System.out.println("*** ユーザーデータテーブル(user_data)のデータ Truncate前 ***");
        List<UserData> userDataList = userDataMapperMs.findAll();
        for (UserData userData : userDataList) {
            System.out.println(userData);
        }
        System.out.println();

        // ユーザーデータを削除
        userDataMapperMs.truncateUserData();

        // ユーザーデータ削除した後のデータを確認
        System.out.println("*** ユーザーデータテーブル(user_data)のデータ Truncate後 ***");
        userDataList = userDataMapperMs.findAll();
        System.out.println(userDataList);
        assertEquals(0, userDataList.size());
    }

    /**
     * 各テストメソッドを実行した後に行う処理を定義する.
     */
    @AfterEach
    public void afterTestClass() {
        System.out.println("*** UserDataTruncateTestMsクラス テスト結果 end ***");
        System.out.println();
    }
}
