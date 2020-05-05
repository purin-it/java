package com.example.demo;

import lombok.Data;

@Data
public class DemoSub2Obj extends DemoSuperObj {

    private String subStr2;

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("DemoSub2Obj(");
        sb.append("subStr2=");
        sb.append(subStr2);
        sb.append(", ");
        sb.append(super.toString());
        sb.append(")");
        return sb.toString();
    }

}
