package com.example.demo;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.csv.CsvDataSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.File;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

// Spring　BootのDIを利用するため、SpringRunnerクラスで、
// @SpringBootTestアノテーションを付与して実行
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDataTestUpdate extends UserDataTestBase{

    @Autowired
    private UserDataMapper userDataMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    protected IDataSet getIDataSet(){
        IDataSet iDataset = null;
        try{
            iDataset = new CsvDataSet(
                    new File(System.getProperty("user.dir")
                            + "\\src\\test\\resources\\com\\example\\demo\\csv"));
        }catch (Exception e){
            System.err.println(e);
        }
        return iDataset;
    }

    /**
     * ユーザーデータへのデータ追加処理確認
     */
    @Test
    public void userDataMapperCreateTest(){
        System.out.println("*** userDataMapperCreateTest started. ***");

        // ユーザーデータへのデータ追加処理
        UserData userData = getInsertUserData();
        userDataMapper.create(userData);

        // 追加されたユーザーデータの確認
        UserData userDataInsert = userDataMapper.findById(Long.valueOf("3"));
        System.out.println("追加後の値　：　" + userDataInsert.toString());
        UserData userDataExpected = getInsertUserData();
        userDataExpected.setSex_value("男");
        assertEquals(userDataExpected.toString(), userDataInsert.toString());

        System.out.println("*** userDataMapperCreateTest ended. ***");
    }

    /**
     * ユーザーデータへのデータ更新処理確認
     */
    @Test
    public void userDataMapperUpdateTest(){
        System.out.println("*** userDataMapperUpdateTest started. ***");

        // 更新用ユーザーデータの作成
        UserData userData = getUpdatedUserData();

        // ユーザーデータの更新
        userDataMapper.update(userData);

        // 更新されたユーザーデータの確認
        UserData userDataUpdate = userDataMapper.findById(Long.valueOf("1"));
        System.out.println("更新後の値　：　" + userDataUpdate.toString());
        UserData userDataExpected = getUpdatedUserData();
        userDataExpected.setSex_value("女");
        assertEquals(userDataExpected.toString(), userDataUpdate.toString());

        System.out.println("*** userDataMapperUpdateTest ended. ***");
    }

    /**
     * ユーザーデータへのデータ削除処理確認
     */
    @Test
    public void userDataMapperDeleteTest(){
        System.out.println("*** userDataMapperDeleteTest started. ***");

        // ユーザーデータの削除
        userDataMapper.deleteById(Long.valueOf("2"));

        // 削除されたユーザーデータの確認
        UserData userDataDelete = userDataMapper.findById(Long.valueOf("2"));
        System.out.println("削除後ユーザーデータ　：　" + userDataDelete);
        assertNull(userDataDelete);

        System.out.println("*** userDataMapperDeleteTest ended. ***");
    }

    /**
     * 追加用ユーザーデータを生成
     * @return 追加用ユーザーデータ
     */
    private UserData getInsertUserData(){
        UserData userData = new UserData();
        userData.setId(3);
        userData.setName("テスト　プリン３");
        userData.setBirthY(2014);
        userData.setBirthM(1);
        userData.setBirthD(15);
        userData.setSex("1");
        return userData;
    }

    /**
     * 更新用ユーザーデータを生成
     * @return 更新用ユーザーデータ
     */
    private UserData getUpdatedUserData(){
        UserData userData = new UserData();
        userData.setId(1);
        userData.setName("テスト　プリン４");
        userData.setBirthY(2015);
        userData.setBirthM(2);
        userData.setBirthD(16);
        userData.setSex("2");
        return userData;
    }
}
