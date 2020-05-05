package com.example.demo;

import lombok.Data;

@Data
public class BindingResultRejectValueModel {

    private String errorCode;

    private Object[] errorArgs;

    private String defaultMessage;
}
