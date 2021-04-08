package com.example.demo;

import com.example.demo.mapper.ms.UserDataMapperMs;
import com.example.demo.mapper.ora.UserDataMapperOra;
import com.example.demo.mapper.ps.UserDataMapperPs;
import com.example.demo.mapper.ss.UserDataMapperSs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    /** OracleのUSER_DATAテーブルにアクセスするMapper */
    @Autowired
    private UserDataMapperOra userDataMapperOra;

    /** MySQLのUSER_DATAテーブルにアクセスするMapper */
    @Autowired
    private UserDataMapperMs userDataMapperMs;

    /** PostgreSQLのUSER_DATAテーブルにアクセスするMapper */
    @Autowired
    private UserDataMapperPs userDataMapperPs;

    /** SQL ServerのUSER_DATAテーブルにアクセスするMapper */
    @Autowired
    private UserDataMapperSs userDataMapperSs;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

    @Override
    public void run(String... args) {
	    // IN句を指定したSQLの実行結果を確認する
        // 引数のIDリストに1000件のデータを指定した場合(Oracle)
        ArrayList<Long> idList = getIdListOneThousand();
        System.out.println("*** 引数のIDリストに1000件のデータを指定した場合(Oracle) ***");
        System.out.println("*** idList size : " + idList.size());

        ArrayList<UserData> userDataList = userDataMapperOra.findByIdList(idList);
        System.out.println("*** userDataList size : " + userDataList.size());
        System.out.println();

        // 引数のIDリストに1001件のデータを指定した場合(Oracle)
        idList = getIdListOneThousandOne();
        System.out.println("*** 引数のIDリストに1001件のデータを指定した場合(Oracle) ***");
        System.out.println("*** idList size : " + idList.size());
        try{
            userDataMapperOra.findByIdList(idList);
        }catch(Exception ex){
            System.out.println("*** " + ex.getCause());
        }

        // 引数のIDリストに1001件のデータを指定した場合(MySQL)
        System.out.println("*** 引数のIDリストに1001件のデータを指定した場合(MySQL) ***");
        System.out.println("*** idList size : " + idList.size());

        userDataList = userDataMapperMs.findByIdList(idList);
        System.out.println("*** userDataList size : " + userDataList.size());
        System.out.println();

        // 引数のIDリストに1001件のデータを指定した場合(PostgreSQL)
        System.out.println("*** 引数のIDリストに1001件のデータを指定した場合(PostgreSQL) ***");
        System.out.println("*** idList size : " + idList.size());

        userDataList = userDataMapperPs.findByIdList(idList);
        System.out.println("*** userDataList size : " + userDataList.size());
        System.out.println();

        // 引数のIDリストに1001件のデータを指定した場合(SQLServer)
        System.out.println("*** 引数のIDリストに1001件のデータを指定した場合(SQLServer) ***");
        System.out.println("*** idList size : " + idList.size());

        userDataList = userDataMapperSs.findByIdList(idList);
        System.out.println("*** userDataList size : " + userDataList.size());
        System.out.println();
    }

    /**
     * 1～1000までのLong型のリストを返却する
     * @return 1～1000までのLong型のリスト
     */
    private ArrayList<Long> getIdListOneThousand(){
        ArrayList<Long> idList = new ArrayList<>();
        for(int i = 1; i <= 1000; i++){
            idList.add(Long.valueOf(i));
        }
	    return idList;
    }

    /**
     * 1～1001までのLong型のリストを返却する
     * @return 1～1001までのLong型のリスト
     */
    private ArrayList<Long> getIdListOneThousandOne(){
        ArrayList<Long> idList = getIdListOneThousand();
        idList.add(Long.valueOf(1001));
        return idList;
    }
}
