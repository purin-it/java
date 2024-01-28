package com.example.demo;

public enum SexEnum {

    MAN("1", "男")
    ,WOMAN("2", "女");

    private String sex;
    private String sex_value;

    SexEnum(String sex, String sex_value){
        this.sex = sex;
        this.sex_value = sex_value;
    }

    public String getSex(){
        return this.sex;
    }

    public String getSex_value(){
        return sex_value;
    }

}
