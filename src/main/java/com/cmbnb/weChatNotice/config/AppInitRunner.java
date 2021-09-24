package com.cmbnb.weChatNotice.config;

import com.cmbnb.weChatNotice.modules.cbs.service.CbsService;
import com.cmbnb.weChatNotice.modules.task.application.SchedulingRunnable;
import com.cmbnb.weChatNotice.modules.task.config.CronTaskRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInitRunner implements ApplicationRunner {

    @Autowired
    CbsService cbsService;

    @Autowired
    CronTaskRegistrar cronTaskRegistrar;



    @Value("${config.checkDzhdCron}")
    private String checkDzhdCron;


    @Override
    public void run(ApplicationArguments args) throws Exception {


//        SchedulingRunnable checkDzhdTask = new SchedulingRunnable("cbsService",
//                "handleTodayDetailAndNotice",
//                null);
//        cronTaskRegistrar.addCronTask(checkDzhdTask, checkDzhdCron);
//
//        System.out.println("application 启动， 把cbsService方法 加入 定时任务中.... ");
    }
}


