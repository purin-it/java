package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    /**
     * 性能検証を行うためのサービス
     */
    @Autowired
    private DemoService demoService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            // 性能検証を行うためのサービスを呼び出す
            demoService.verifyPerformance();
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

}
