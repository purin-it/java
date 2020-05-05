package com.example.demo;

public class DemoUtil {

    public static <T extends DemoSuperObj> T makeArgClassObj(Class<T> clazz){
        T obj = null;
        try{
            obj = clazz.getDeclaredConstructor().newInstance();
            obj.setSuperStr1("superStr1");
            obj.setSuperStr2("superStr2");
        }catch (Exception ex){
            System.err.println(ex);
        }
        return obj;
    }

}
