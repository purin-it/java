package com.example.demo;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.csv.CsvDataSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.File;

// Spring　BootのDIを利用するため、SpringRunnerクラスで、
// @SpringBootTestアノテーションを付与して実行
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDataTestCsv extends UserDataTestBase{

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
     * ユーザーデータのテーブルデータを取得し、結果を確認
     */
    @Test
    public void userDataMapperFindByIdTest(){
        UserData userData = userDataMapper.findById(Long.valueOf("1"));
        System.out.println("取得した値　：　" + userData.toString());
        assertEquals(super.getExpectedUserData().toString(), userData.toString());
    }
}
