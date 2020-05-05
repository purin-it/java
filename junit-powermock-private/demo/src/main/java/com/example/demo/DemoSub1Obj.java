package com.example.demo;

import lombok.Data;

@Data
public class DemoSub1Obj extends DemoSuperObj {

    private String subStr1;

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("DemoSub1Obj(");
        sb.append("subStr1=");
        sb.append(subStr1);
        sb.append(", ");
        sb.append(super.toString());
        sb.append(")");
        return sb.toString();
    }
}
