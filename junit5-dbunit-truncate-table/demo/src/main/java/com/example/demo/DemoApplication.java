package com.example.demo;

import com.example.demo.mapper.UserData;
import com.example.demo.mapper.ms.UserDataMapperMs;
import com.example.demo.mapper.ora.UserDataMapperOra;
import com.example.demo.mapper.ps.UserDataMapperPs;
import com.example.demo.mapper.ss.UserDataMapperSs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

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
	    System.out.println("*** Oracle userDataMapperOra.findAll ***");
	    List<UserData> userDataListOra = userDataMapperOra.findAll();
	    System.out.println(userDataListOra);
	    System.out.println();

        System.out.println("*** MySQL userDataMapperMs.findAll　***");
        List<UserData> userDataListMs = userDataMapperMs.findAll();
        System.out.println(userDataListMs);
        System.out.println();

        System.out.println("*** PostgreSQL　userDataMapperPs.findAll ***");
        List<UserData> userDataListPs = userDataMapperPs.findAll();
        System.out.println(userDataListPs);
        System.out.println();

        System.out.println("*** SQL Server　userDataMapperSs.findAll ***");
        List<UserData> userDataListSs = userDataMapperSs.findAll();
        System.out.println(userDataListSs);
        System.out.println();
    }

}
