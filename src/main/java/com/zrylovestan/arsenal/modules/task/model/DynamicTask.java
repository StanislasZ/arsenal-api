package com.zrylovestan.arsenal.modules.task.model;


import lombok.Data;



/**
 * 动态定时任务model
 **/
@Data
public class DynamicTask {


    private Integer id;

    private String cron;
}
