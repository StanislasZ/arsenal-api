package com.zrylovestan.arsenal.modules.jacksonTest.entity;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ExtendableBean {

    private String name;

    private Map<String, String> properties = new HashMap<>();

    @JsonAnyGetter
    public Map<String, String> getProperties() {
        return properties;
    }

    /**
     * Jackson反序列化时， 能找到getter方法的，就用
     * 找不到的，全部塞到 properties里
     * @param key
     * @param value
     */
    @JsonAnySetter
    public void add(String key, String value) {
        properties.put(key, value);
    }




}