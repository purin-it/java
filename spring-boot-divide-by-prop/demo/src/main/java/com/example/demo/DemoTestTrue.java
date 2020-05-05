package com.example.demo;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

//demo.test=trueの場合に呼ばれる処理
@ConditionalOnProperty(name = "demo.test", havingValue = "true")
@Component
public class DemoTestTrue implements DemoTest {

    /**
     * {@inheritDoc}
     */
    @Override
    public String gestTestValue() {
        return "demo.test.value = true";
    }

}
