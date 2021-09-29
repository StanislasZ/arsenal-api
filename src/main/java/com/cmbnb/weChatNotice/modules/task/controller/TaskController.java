package com.cmbnb.weChatNotice.modules.task.controller;

import com.alibaba.fastjson.JSONObject;
import com.cmbnb.weChatNotice.core.util.CommonUtils;
import com.cmbnb.weChatNotice.modules.task.application.SchedulingRunnable;
import com.cmbnb.weChatNotice.modules.task.config.CronTaskRegistrar;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
