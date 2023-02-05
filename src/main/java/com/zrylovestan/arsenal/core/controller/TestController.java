package com.zrylovestan.arsenal.core.controller;

import com.alibaba.fastjson.JSONObject;
import com.zrylovestan.arsenal.core.base.CommonJsonException;
import com.zrylovestan.arsenal.core.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {


    @GetMapping("/testIgnoreUnknownField")
    public Object testJacksonIgnoreUnknownField() {
        return "{\"name\":\"zz\",\"name1\":123,\"field1\":\"1\",\"field2\":\"2\"}";
    }

    @RequestMapping("/abcd")
    public JSONObject test1() {
        JSONObject jo = new JSONObject();
        jo.put("abcd", "abcd");
        return CommonUtils.successJson(jo);
    }
    @RequestMapping("/testError")
    public JSONObject testError() {
        try {
            if (1<2) {

                throw new CommonJsonException("测试错误");
            }
        } catch (Exception ex) {
            log.error("测试错误", ex);
        }

       return CommonUtils.successJson();
    }

}
