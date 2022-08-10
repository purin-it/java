package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    /**
     * 性能測定を行うための個別サービス
     */
    @Autowired
    private DemoServiceSub demoServiceSub;

    /**
     * 性能検証を行うためのサービス
     */
    public void verifyPerformance() {
        System.out.println("com.example.demo.DemoService.verifyPerformance start.");
        System.out.println();

        System.out.println("=== 1回目の性能測定を行います. ===");
        demoServiceSub.verifyPerformanceEach();
        System.out.println();

        System.out.println("=== 2回目の性能測定を行います. ===");
        demoServiceSub.verifyPerformanceEach();
        System.out.println();

        System.out.println("com.example.demo.DemoService.verifyPerformance end.");
    }

}
