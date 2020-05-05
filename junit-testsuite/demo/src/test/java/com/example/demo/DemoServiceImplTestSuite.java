package com.example.demo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DemoServiceImplTest.class
        , DemoServiceImplTest2.class
        , DemoServiceImplTest3.class
})
public class DemoServiceImplTestSuite {
}
