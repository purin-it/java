package com.example.demo;

import com.example.demo.mapper.ss.UserDataMapperSs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    /**
     * SQL ServerのUSER_DATAテーブルにアクセスするMapper
     */
    @Autowired
    private UserDataMapperSs userDataMapperSs;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        // 引数のIDリストに2098件のデータを指定した場合(SQLServer)
        ArrayList<Long> idList = getIdListNoErrorMax();
        System.out.println("*** 引数のIDリストに2098件のデータを指定した場合(SQLServer) ***");
        System.out.println("*** idList size : " + idList.size());

        ArrayList<UserData> userDataList = userDataMapperSs.findByIdList(idList);
        System.out.println("*** userDataList size : " + userDataList.size());
        System.out.println();

        // 引数のIDリストに2099件のデータを指定した場合(SQLServer)
        idList = getIdListErrorMin();
        System.out.println("*** 引数のIDリストに2099件のデータを指定した場合(SQLServer) ***");
        System.out.println("*** idList size : " + idList.size());

        try {
            userDataMapperSs.findByIdList(idList);
        } catch (Exception ex) {
            System.out.println("*** " + ex.getCause());
        }
    }

    /**
     * 1～2098までのLong型のリストを返却する
     * @return 1～2098までのLong型のリスト
     */
    private ArrayList<Long> getIdListNoErrorMax() {
        ArrayList<Long> idList = new ArrayList<>();
        for (int i = 1; i <= 2098; i++) {
            idList.add(Long.valueOf(i));
        }
        return idList;
    }

    /**
     * 1～2099までのLong型のリストを返却する
     * @return 1～2099までのLong型のリスト
     */
    private ArrayList<Long> getIdListErrorMin() {
        ArrayList<Long> idList = getIdListNoErrorMax();
        idList.add(Long.valueOf(2099));
        return idList;
    }
}
