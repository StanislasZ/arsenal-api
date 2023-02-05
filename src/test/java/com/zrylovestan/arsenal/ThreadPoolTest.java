package com.zrylovestan.arsenal;


import com.zrylovestan.arsenal.modules.threadPoolTest.service.AsyncTestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ThreadPoolTest {


    @Resource
    AsyncTestService asyncTestService;

    @Resource
    ThreadPoolTaskExecutor threadPoolTaskExecutor;


    @Test
    public void test1() throws InterruptedException {
        asyncTestService.hello("stan");
        Thread.sleep(2000);
    }


}
