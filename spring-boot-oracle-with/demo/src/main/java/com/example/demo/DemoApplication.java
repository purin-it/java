package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    /** 指定する上司の生年月日 */
    private static final String BOSS_BIRTHDAY = "1965/05/15";

    /**
     * Employeeテーブルへアクセスするマッパー
     */
    @Autowired
    private EmployeeMapper employeeMapper;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        // 指定した生年月日の上司をもつEmployeeテーブルのデータを取得する(IN句)を呼び出す
        List<Employee> empListIn = employeeMapper.findByBossBirthdayIn(BOSS_BIRTHDAY);

        // 指定した生年月日の上司をもつEmployeeテーブルのデータを表示
        System.out.println("*** EmployeeMapper.findByBossBirthdayIn 実行結果 START ***");
        for(Employee emp : empListIn){
            System.out.println(emp);
        }
        System.out.println("*** EmployeeMapper.findByBossBirthdayIn 実行結果 END ***");
        System.out.println();

        // 指定した生年月日の上司をもつEmployeeテーブルのデータを取得する(WITH句)を呼び出す
        List<Employee> empListWith = employeeMapper.findByBossBirthdayWith(BOSS_BIRTHDAY);

        // 指定した生年月日の上司をもつEmployeeテーブルのデータを表示
        System.out.println("*** EmployeeMapper.findByBossBirthdayWith 実行結果 START ***");
        for(Employee emp : empListWith){
            System.out.println(emp);
        }
        System.out.println("*** EmployeeMapper.findByBossBirthdayWith 実行結果 END ***");
    }

}
