package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    /**
     * 生年月日を検索条件にユーザーデータテーブル(user_data)の
     * データを取得するサービス
     */
    @Autowired
    private DemoService demoService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        // 生年月日を検索条件にユーザーデータテーブル(user_data)の
        // データを取得する
        demoService.getUserDataByBirthday();
    }

}
