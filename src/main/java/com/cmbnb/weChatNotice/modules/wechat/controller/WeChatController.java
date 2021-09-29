package com.cmbnb.weChatNotice.modules.wechat.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.cmbnb.weChatNotice.core.util.CommonUtils;
import com.cmbnb.weChatNotice.modules.clientInfo.entity.ClientInfo;
import com.cmbnb.weChatNotice.modules.wechat.entity.WechatUser;
import com.cmbnb.weChatNotice.modules.wechat.entity.request.WechatUserQo;
import com.cmbnb.weChatNotice.modules.wechat.service.WeChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

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

    @RequestMapping("/getWechatUserList")
    public JSONObject getWechatUserList(@RequestBody WechatUserQo qo) throws IOException {
        log.info("getWechatUserList..  qo = {}", qo);
        return CommonUtils.successPage(weChatService.getWechatUserList(qo));
    }


    @RequestMapping("/exportWechatUserExcel")
    public void exportWechatUserExcel(HttpServletResponse response) throws Exception {
        log.info("exportWechatUserExcel.. ");

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");

        String fileName = URLEncoder.encode("付款回单微信通知人员", "UTF-8").replaceAll("\\+", "%20");
        Class clazz = WechatUser.class;

        List<WechatUser> clientInfos = weChatService.getWechatUserExportList();

        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), clazz).sheet("Sheet1").doWrite(clientInfos);
    }



}
