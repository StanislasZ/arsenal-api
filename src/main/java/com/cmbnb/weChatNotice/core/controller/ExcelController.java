package com.cmbnb.weChatNotice.core.controller;


import com.alibaba.excel.EasyExcel;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/excel")
public class ExcelController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelController.class);


//    @GetMapping("/dlTemplate/{name}")
//    public void dlTemplate(@PathVariable("name") String name,
//                           @RequestParam("matchFlag") String matchFlag,
//                           HttpServletResponse response) throws IOException {
//        LOGGER.info("name = {}", name);
//        LOGGER.info("matchFlag = {}", matchFlag);
//        response.setContentType("application/vnd.ms-excel");
//        response.setCharacterEncoding("utf-8");
//        String fileName = "";
//        Class clazz = null;
//        List list = null;
//        if (name.equals("QY")) {
//            fileName = URLEncoder.encode("抵押信息请求模板", "UTF-8").replaceAll("\\+", "%20");
//            clazz = Qy.class;
//            list = new ArrayList();
//        }
//        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
//        EasyExcel.write(response.getOutputStream(), clazz).sheet("Sheet1").doWrite(list);
//    }
}
