package net.zhaoyu.javapros.j2se.threads.ThreadPool.springconfig;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description:
 * @Author: zhaoyu
 * @Date: 2020/10/22
 */
@Configuration
@EnableAsync
@CommonsLog
public class MyAsyncConfigurer implements AsyncConfigurer {
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2); // 设置线程池初始化线程数量
        executor.setMaxPoolSize(5); // 设置线程池最大线程数量
        executor.setQueueCapacity(10);// 设置缓冲队列的大小
        executor.setKeepAliveSeconds(200);// 线程的允许空闲时间（单位：s）
        executor.setThreadNamePrefix("Async-Service-"); // 线程名称前缀
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy()); // 设置线程拒绝策略
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> {
            log.error("Async Exception message - " + throwable.getMessage());
            log.error("Method name - " + method.getName());
            for (Object param : objects) {
                log.info("Parameter value - " + param);
            }
        };
    }
}
