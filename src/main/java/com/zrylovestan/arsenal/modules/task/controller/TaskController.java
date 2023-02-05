package com.zrylovestan.arsenal.modules.task.controller;

import com.alibaba.fastjson.JSONObject;
import com.zrylovestan.arsenal.core.util.CommonUtils;
import com.zrylovestan.arsenal.modules.task.config.CronTaskRegistrar;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
@Slf4j
public class TaskController {


    @Autowired
    CronTaskRegistrar cronTaskRegistrar;




    @RequestMapping("/clearAllTask")
    public JSONObject clearAllTask() {
        cronTaskRegistrar.destroy();
        return CommonUtils.successJson();
    }
}
