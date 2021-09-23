package com.cmbnb.weChatNotice.core.controller;

import com.alibaba.fastjson.JSONObject;
import com.cmbnb.weChatNotice.core.util.CommonUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/abcd")
    public JSONObject test1() {
        JSONObject jo = new JSONObject();
        jo.put("abcd", "abcd");
        return CommonUtils.successJson(jo);
    }
}
