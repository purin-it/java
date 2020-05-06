package com.example.demo;

import org.springframework.stereotype.Service;

@Service
public class DemoService {
    public String getString(){
        return "from DemoService";
    }
}
