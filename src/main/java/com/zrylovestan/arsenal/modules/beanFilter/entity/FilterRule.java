package com.zrylovestan.arsenal.modules.beanFilter.entity;


import lombok.Data;

@Data
public class FilterRule {

    private Long id;

    private String busId;

    private String ruleType;

    private String targetField;

    private String operator;

    private String opValue;
}
