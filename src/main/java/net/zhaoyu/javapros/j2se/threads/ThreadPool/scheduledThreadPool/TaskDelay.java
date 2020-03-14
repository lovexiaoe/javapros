package net.zhaoyu.javapros.j2se.threads.ThreadPool.scheduledThreadPool;

import java.util.Date;
import java.util.concurrent.*;

/**
 * ScheduledThreadPool使用例子，任务执行设定延迟时间
 */
public class TaskDelay {

    public static void main(String[] args) {
        ScheduledExecutorService executor= Executors.newScheduledThreadPool(1);

        // 启动5个任务，每个任务延迟i+1秒
        for (int i=0; i<5; i++) {
            TaskA task=new TaskA("TaskPeriodically "+i);
            //schedule方法指定任务执行的延迟时间。
            executor.schedule(task,i+1 , TimeUnit.SECONDS);
        }

        executor.shutdown();
        // Waits for the finalization of the executor
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
class TaskA implements Callable<String> {

    private final String name;

    public TaskA(String name) {
        this.name=name;
    }

    @Override
    public String call() throws Exception {
        System.out.printf("%s: Starting at : %s\n",name,new Date());
        return "Hello, world";
    }

}