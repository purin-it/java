package com.example.demo;

import com.example.demo.mapper.ms.EmployeeMapperMs;
import com.example.demo.mapper.ora.EmployeeMapperOra;
import com.example.demo.mapper.ps.EmployeeMapperPs;
import com.example.demo.mapper.ss.EmployeeMapperSs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    /** 指定する上司の生年月日 */
    private static final String BOSS_BIRTHDAY = "1965/05/15";

    /** OracleのEmployeeテーブルにアクセスするMapper */
    @Autowired
    private EmployeeMapperOra employeeMapperOra;

    /** MySQLのEmployeeテーブルにアクセスするMapper */
    @Autowired
    private EmployeeMapperMs employeeMapperMs;

    /** PostgreSQLのEmployeeテーブルにアクセスするMapper */
    @Autowired
    private EmployeeMapperPs employeeMapperPs;

    /** SQL ServerのEmployeeテーブルにアクセスするMapper */
    @Autowired
    private EmployeeMapperSs employeeMapperSs;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

    @Override
    public void run(String... args) {
        // Oracle上で指定した生年月日の上司をもつEmployeeテーブルのデータを取得する(WITH句)を呼び出す
        List<Employee> empListWithOra = employeeMapperOra.findByBossBirthdayWith(BOSS_BIRTHDAY);

        // 指定した生年月日の上司をもつEmployeeテーブルのデータを表示
        System.out.println("*** Oracle EmployeeMapperOra.findByBossBirthdayWith 実行結果 START ***");
        for(Employee emp : empListWithOra){
            System.out.println(emp);
        }
        System.out.println("*** Oracle EmployeeMapperOra.findByBossBirthdayWith 実行結果 END ***");
        System.out.println();

        // MySQL上で指定した生年月日の上司をもつEmployeeテーブルのデータを取得する(WITH句)を呼び出す
        List<Employee> empListWithMs = employeeMapperMs.findByBossBirthdayWith(BOSS_BIRTHDAY);

        // 指定した生年月日の上司をもつEmployeeテーブルのデータを表示
        System.out.println("*** MySQL EmployeeMapperMs.findByBossBirthdayWith 実行結果 START ***");
        for(Employee emp : empListWithMs){
            System.out.println(emp);
        }
        System.out.println("*** MySQL EmployeeMapperMs.findByBossBirthdayWith 実行結果 END ***");
        System.out.println();

        // PostgreSQL上で指定した生年月日の上司をもつEmployeeテーブルのデータを取得する(WITH句)を呼び出す
        List<Employee> empListWithPs = employeeMapperPs.findByBossBirthdayWith(BOSS_BIRTHDAY);

        // 指定した生年月日の上司をもつEmployeeテーブルのデータを表示
        System.out.println("*** PostgreSQL EmployeeMapperPs.findByBossBirthdayWith 実行結果 START ***");
        for(Employee emp : empListWithPs){
            System.out.println(emp);
        }
        System.out.println("*** PostgreSQL EmployeeMapperPs.findByBossBirthdayWith 実行結果 END ***");
        System.out.println();

        // SQL Server上で指定した生年月日の上司をもつEmployeeテーブルのデータを取得する(WITH句)を呼び出す
        List<Employee> empListWithSs = employeeMapperSs.findByBossBirthdayWith(BOSS_BIRTHDAY);

        // 指定した生年月日の上司をもつEmployeeテーブルのデータを表示
        System.out.println("*** SQL Server EmployeeMapperSs.findByBossBirthdayWith 実行結果 START ***");
        for(Employee emp : empListWithSs){
            System.out.println(emp);
        }
        System.out.println("*** SQL Server EmployeeMapperSs.findByBossBirthdayWith 実行結果 END ***");
        System.out.println();
    }

}
