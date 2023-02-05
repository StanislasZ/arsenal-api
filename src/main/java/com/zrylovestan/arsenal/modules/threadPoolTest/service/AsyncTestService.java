package com.zrylovestan.arsenal.modules.threadPoolTest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AsyncTestService {

    @Async
    public void hello(String name) {
        log.info("异步线程启动 started. " + name);
    }
}
