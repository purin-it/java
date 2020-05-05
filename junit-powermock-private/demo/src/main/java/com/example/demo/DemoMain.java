package com.example.demo;

public class DemoMain {

    public static void main(String[] args){
        DemoSub1Obj demoSub1Obj = DemoUtil.makeArgClassObj(DemoSub1Obj.class);
        System.out.println(demoSub1Obj.toString());

        DemoSub2Obj demoSub2Obj = DemoUtil.makeArgClassObj(DemoSub2Obj.class);
        System.out.println(demoSub2Obj.toString());
    }
}
