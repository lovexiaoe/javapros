package net.zhaoyu.javapros.j2se.threads.ThreadPool.scheduledThreadPool;

import java.util.Date;
import java.util.concurrent.*;

/**
 * 任务周期性执行。任务的执行间隔是任务启动的间隔，如，有一个运行时间是5秒的任务，你设置3秒的时间间隔，那么，在同一时间
 * ，可能会有两个任务同时执行。sleep可能会影响到任务开始执行的时间。
 */
public class TaskPeriodically {
    public static void main(String[] args) {
        ScheduledExecutorService executor=Executors.newScheduledThreadPool(1);
        // 创建一个任务，将它发送给executor，执行器将在1秒后执行，每隔两秒执行一次。
        Task task=new Task("Task");
        /**
         * ScheduledFuture实现了Future接口和Delay接口。task是一个Runnable，它并没有参数，所以这里使用ScheduledFuture<?>
         */
        ScheduledFuture<?> result=executor.scheduleAtFixedRate(task, 1, 2, TimeUnit.SECONDS);

        // 使用ScheduledFuture的getDelay方法返回到下一个任务执行还需要的时间。
        for (int i=0; i<10; i++){
            System.out.printf("Main: Delay: %d\n",result.getDelay(TimeUnit.MILLISECONDS));
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();

        // Verify that the periodic tasks no is in execution after the executor shutdown()
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Task implements Runnable {
    private final String name;

    public Task(String name) {
        this.name=name;
    }

    @Override
    public void run() {
        System.out.printf("%s: Executed at: %s\n",name,new Date());
    }

}
