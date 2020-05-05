package com.example.demo;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

//demo.test=falseの場合に呼ばれる処理
@ConditionalOnProperty(name = "demo.test", havingValue = "false")
@Component
public class DemoTestFalse implements DemoTest {

    /**
     * {@inheritDoc}
     */
    @Override
    public String gestTestValue() {
        return "demo.test.value = false";
    }

}
