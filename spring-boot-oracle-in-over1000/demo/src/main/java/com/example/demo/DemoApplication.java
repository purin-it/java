package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired
    private UserDataMapper userDataMapper;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

    @Override
    public void run(String... args) {
	    // IN句を指定したSQLの実行結果を確認する
        // 引数のIDリストに1000件のデータを指定した場合
        ArrayList<Long> idList = getIdListOneThousand();
        System.out.println("*** 引数のIDリストに1000件のデータを指定した場合 ***");
        System.out.println("*** idList size : " + idList.size());

        ArrayList<UserData> userDataList = userDataMapper.findByIdList(idList);
        System.out.println("*** userDataList size : " + userDataList.size());
        System.out.println();

        // 引数のIDリストに1001件のデータを指定した場合
        idList = getIdListOneThousandOne();
        System.out.println("*** 引数のIDリストに1001件のデータを指定した場合 ***");
        System.out.println("*** idList size : " + idList.size());
        try{
            userDataMapper.findByIdList(idList);
        }catch(Exception ex){
            System.out.println("*** " + ex.getCause());
        }
    }

    private ArrayList<Long> getIdListOneThousand(){
        ArrayList<Long> idList = new ArrayList<>();
        for(int i = 1; i <= 1000; i++){
            idList.add(Long.valueOf(i));
        }
	    return idList;
    }

    private ArrayList<Long> getIdListOneThousandOne(){
        ArrayList<Long> idList = getIdListOneThousand();
        idList.add(Long.valueOf(1001));
        return idList;
    }
}
