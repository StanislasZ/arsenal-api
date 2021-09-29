package com.cmbnb.weChatNotice.modules.clientInfo.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cmbnb.weChatNotice.core.util.CommonUtils;
import com.cmbnb.weChatNotice.modules.clientInfo.entity.ClientInfo;
import com.cmbnb.weChatNotice.modules.clientInfo.entity.request.ClientInfoQo;
import com.cmbnb.weChatNotice.modules.clientInfo.service.ClientInfoService;
import com.cmbnb.weChatNotice.modules.wechat.entity.request.WechatUserQo;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/clientInfo")
@Slf4j
public class ClientInfoController {

    @Autowired
    ClientInfoService clientInfoService;



    @RequestMapping("/updateClientInfo")
    public JSONObject updateClientInfo(@RequestParam("file") MultipartFile file) throws IOException {
        clientInfoService.updateClientInfoFromExcel(file);
        return CommonUtils.successJson("更新成功");
    }

    @RequestMapping("/getClientInfoList")
    public JSONObject getClientInfoList(@RequestBody ClientInfoQo qo) throws IOException {
        log.info("getClientInfoList..  qo = {}", qo);
        return CommonUtils.successPage(clientInfoService.getClientInfoList(qo));
    }



    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response) throws Exception {
        log.info("exportExcel.. ");

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");

        String fileName = URLEncoder.encode("客户信息", "UTF-8").replaceAll("\\+", "%20");
        Class clazz = ClientInfo.class;

        List<ClientInfo> clientInfos = clientInfoService.getExportList();

        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), clazz).sheet("Sheet1").doWrite(clientInfos);
    }


}
