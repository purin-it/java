package com.example.demo;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import java.sql.Connection;
import java.sql.DriverManager;

public abstract class UserDataTestBase {

    /**
     * テストケース(サブクラスのuserDataMapperFindByIdTestメソッド)実行前に
     * データベースの指定テーブルデータを更新する処理
     */
    @Before
    public void setUp(){
        IDatabaseConnection connection = null;
        try{
            // データベース接続用コネクションを取得
            connection = this.getDatabaseConnection();
            // データベースに追加するデータファイルを指定
            IDataSet iDataset = this.getIDataSet();
            // データベースの指定テーブルデータを、全データ削除後に、
            // 追加するデータファイルの内容に変更
            DatabaseOperation.CLEAN_INSERT.execute(connection, iDataset);
        }catch (Exception e){
            System.err.println(e);
        }finally {
            if(connection != null){
                try{
                    // データベース接続用コネクションをクローズ
                    connection.close();
                }catch (Exception e){
                    System.err.println(e);
                }
            }
        }
    }

    /**
     * データベースに追加するデータファイルを指定
     * (本実装はサブクラスで行う)
     * @return データセットオブジェクト
     */
    protected abstract IDataSet getIDataSet();

    /**
     * Oracleデータベース接続コネクションを取得
     * @return Oracleデータベース接続コネクション
     */
    private IDatabaseConnection getDatabaseConnection(){
        try{
            // Oracleデータベース接続用ドライバクラスを指定
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // Oracleデータベース接続コネクションに接続URL,ユーザーID,パスワードを指定
            Connection jdbcConnection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe","USER01" ,"USER01");
            // 返却用データベースコネクションを取得
            // その際、第二引数にスキーマ名を指定(そうしないとAmbiguousTableNameExceptionが発生する)
            IDatabaseConnection iDatabaseConnection = new DatabaseConnection(jdbcConnection, "USER01");
            // データベースコネクションを返却
            return iDatabaseConnection;
        }catch (Exception e){
            System.err.println(e);
        }
        return null;
    }

    /**
     * 想定結果となるUserDataオブジェクトを取得
     * @return 想定結果となるUserDataオブジェクト
     */
    protected UserData getExpectedUserData(){
        UserData userData = new UserData();
        userData.setId(1);
        userData.setName("テスト　プリン");
        userData.setBirthY(2012);
        userData.setBirthM(2);
        userData.setBirthD(28);
        userData.setSex("2");
        userData.setSex_value("女");
        return userData;
    }
}
