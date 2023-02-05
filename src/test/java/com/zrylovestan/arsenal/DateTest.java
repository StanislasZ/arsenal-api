package com.zrylovestan.arsenal;

import com.alibaba.fastjson.JSONObject;
import com.zrylovestan.arsenal.core.util.DateUtil;
import org.junit.Test;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTest {


    @Test
    public void testOutputDir() {
        String projectPath = System.getProperty("user.dir");
        System.out.println(projectPath);
    }


    @Test
    public void testCast() {
        String s = "2022-01-05";


        LocalDate ld = LocalDate.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(ld);

        LocalDate ld2 = ld.minusDays(3);
        System.out.println(ld2);


        System.out.println(ld.plusWeeks(1));
        System.out.println(ld.plusMonths(1));
















    }


    @Test
    public void testIsToday() throws ParseException {



        String s = "2021-09-17 16:21:31.0";
//        String s = "  ";

        System.out.println(DateUtil.isToday(s));



    }
}
