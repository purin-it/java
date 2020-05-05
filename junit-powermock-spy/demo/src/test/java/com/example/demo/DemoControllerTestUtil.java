package com.example.demo;

import org.springframework.ui.Model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DemoControllerTestUtil {

    public static Model getModel(){
        return new Model() {
            private Map<String, Object> modelMap = new HashMap<>();
            @Override
            public Model addAttribute(String attributeName, Object attributeValue) {
                modelMap.put(attributeName, attributeValue);
                return null;
            }

            @Override
            public Model addAttribute(Object attributeValue) {
                return null;
            }

            @Override
            public Model addAllAttributes(Collection<?> attributeValues) {
                return null;
            }

            @Override
            public Model addAllAttributes(Map<String, ?> attributes) {
                return null;
            }

            @Override
            public Model mergeAttributes(Map<String, ?> attributes) {
                return null;
            }

            @Override
            public boolean containsAttribute(String attributeName) {
                return false;
            }

            @Override
            public Map<String, Object> asMap() {
                return modelMap;
            }
        };
    }
}
