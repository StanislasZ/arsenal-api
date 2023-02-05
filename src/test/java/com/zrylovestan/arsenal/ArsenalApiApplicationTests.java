package com.zrylovestan.arsenal;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zrylovestan.arsenal.core.util.CommonUtils;


import com.zrylovestan.arsenal.modules.beanFilter.entity.FilterRule;
import com.zrylovestan.arsenal.modules.beanFilter.service.FilterRuleService;
import com.zrylovestan.arsenal.modules.stu.entity.Stu;
import com.zrylovestan.arsenal.modules.stu.mapper.StuMapper;
import com.zrylovestan.arsenal.modules.stu.service.StuService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArsenalApiApplicationTests {

    @Resource
    StuService stuService;

    @Resource
    FilterRuleService filterRuleService;

    @Test
    public void testNull() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        List<Stu> stus = stuService.getStuList();

        Stu stu1 = stus.get(0);

        stu1.setName(null);
        System.out.println(PropertyUtils.getProperty(stu1, "birth").getClass().getName());
        System.out.println(PropertyUtils.getProperty(stu1, "updateTime").getClass().getName());
        System.out.println(BeanUtils.getProperty(stu1, "birth"));


        System.out.println(stu1.getBirth());
        System.out.println(JSON.toJSONString(stu1));

        System.out.println(stu1.getUpdateTime());


    }



    @Test
    public void getStuList() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        List<Stu> stus = stuService.getStuList();
        System.out.println(JSON.toJSONString(stus));


        String busId = "XFT1001";
        String filterType = "STU";
        List<FilterRule> rules = filterRuleService.getRulesByBusIdAndType(busId, filterType);
        System.out.println("rules = " + JSON.toJSONString(rules));

//        List<FilterRule> diyRules = new ArrayList<>();
//        FilterRule rule = new FilterRule();
//        rule.setBusId("XFT1001");
//        rule.setRuleType("STU");
//        rule.setTargetField("name");
//        rule.setOperator("rlike");
//        rule.setOpValue("2");
//        diyRules.add(rule);

        System.out.println("--------------");
        List<Stu> filteredList = stus
                .stream()
                .filter(filterRuleService.getFilter(rules, Stu.class))
                .collect(Collectors.toList());

        System.out.println("filteredList = " + JSON.toJSONString(filteredList));
    }






}
