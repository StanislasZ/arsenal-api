package com.zrylovestan.arsenal.modules.task.task;

import java.util.concurrent.ScheduledFuture;

/**
 * 定时任务控制类
 **/
public final class ScheduledTask {

    public volatile ScheduledFuture<?> future;


    /**
     * 取消定时任务
     */
    public void cancel() {
        ScheduledFuture<?> future = this.future;
        if (future != null) {
            future.cancel(true);
        }
    }
}
