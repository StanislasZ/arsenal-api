package com.zrylovestan.arsenal.config;

import com.zrylovestan.arsenal.modules.task.application.SchedulingRunnable;
import com.zrylovestan.arsenal.modules.task.config.CronTaskRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInitRunner implements ApplicationRunner {



    @Autowired
    CronTaskRegistrar cronTaskRegistrar;





    @Override
    public void run(ApplicationArguments args) throws Exception {



//        SchedulingRunnable checkDzhdTask = new SchedulingRunnable("cbsService",
//                "handleTodayDetailAndNotice",
//                null);
//        cronTaskRegistrar.addCronTask(checkDzhdTask, checkDzhdCron);


//        SchedulingRunnable testTask = new SchedulingRunnable("cbsService",
//                "testCronTask",
//                null);
//        cronTaskRegistrar.addCronTask(testTask, "0 0/2 * * * ?");


//        System.out.println("application 启动， 把cbsService方法 加入 定时任务中.... ");
    }
}


