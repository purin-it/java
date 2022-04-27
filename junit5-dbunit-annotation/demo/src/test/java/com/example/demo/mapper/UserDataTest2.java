package com.example.demo.mapper;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import java.util.List;

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class
        , DirtiesContextTestExecutionListener.class
        , TransactionDbUnitTestExecutionListener.class
        , DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = DemoXlsDataSetLoader.class
        , databaseConnection = {"dbUnitDatabaseConnection"})
public class UserDataTest2 {

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
        System.out.println("*** UserDataTest2クラス テスト結果 start ***");
    }

    /**
     * テストを実行した後で、expectedDatabaseTest.xlsxに定義したデータが
     * 追加されることを検証する.
     */
    // @ExpectedDatabaseアノテーションで、assertionMode属性に
    // 「DatabaseAssertionMode.NON_STRICT_UNORDERED」を指定することで、
    // expectedDatabaseTest.xlsxに記載のあるテーブル・カラムのみを、行の順序を無視して
    // 検証できるようにしている
    @Test
    @DatabaseSetup("/com/example/demo/xls/databaseSetupTest.xlsx")
    @ExpectedDatabase(value="/com/example/demo/xls/expectedDatabaseTest.xlsx"
            , assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void userDataTest() {
        UserData userDataAdd = new UserData(4, "テスト　プリン４"
                , 2016, 5, 6, "1", "テスト４", "男");
        userDataMapper.create(userDataAdd);

        System.out.println("*** ユーザーデータテーブル(user_data)のデータ ***");
        List<UserData> userDataList = userDataMapper.findAll();
        for (UserData userData : userDataList) {
            System.out.println(userData);
        }
    }

    /**
     * 各テストメソッドを実行した後に行う処理を定義する.
     */
    @After
    public void afterTestClass() {
        System.out.println("*** UserDataTest2クラス テスト結果 end ***");
        System.out.println();
    }
}
