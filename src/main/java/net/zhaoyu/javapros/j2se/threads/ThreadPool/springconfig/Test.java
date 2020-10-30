package net.zhaoyu.javapros.j2se.threads.ThreadPool.springconfig;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * @Description: 调用线程池异步处理
 * @Author: zhaoyu
 * @Date: 2020/10/22
 */
@Service
public class Test {

    @Async
    public void sendMessage(){
        System.out.println("async method!");
    }

    @Async
    public Future<String> asyncSendMessage(){
        return new AsyncResult<String>("future task!");
    }
}
