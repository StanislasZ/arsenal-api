package com.cmbnb.weChatNotice.modules.wechat.controller;

import com.alibaba.fastjson.JSONObject;
import com.cmbnb.weChatNotice.core.util.CommonUtils;
import com.cmbnb.weChatNotice.modules.wechat.service.WeChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/wechat")
@Slf4j
public class WeChatController {

    @Autowired
    WeChatService weChatService;


    @RequestMapping("/getAccessToken")
    public JSONObject getAccessToken() {

        return CommonUtils.successJson(weChatService.getAccessToken());
    }


    @RequestMapping("/updateWechatUser")
    public JSONObject updateWechatUser(@RequestParam("file") MultipartFile file) throws IOException {
        weChatService.updateWechatUserFromExcel(file);
        return CommonUtils.successJson("更新成功");
    }

}
