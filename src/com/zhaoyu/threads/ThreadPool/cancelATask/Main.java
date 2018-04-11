package com.zhaoyu.threads.ThreadPool.cancelATask;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 取消一个在Executor中的任务，可以使用Future的cancel方法。
 */
public class Main {
    public static void main(String[] args) {

        ThreadPoolExecutor executor=(ThreadPoolExecutor) Executors.newCachedThreadPool();
        Task task=new Task();

        // Send the task to the executor
        Future<String> result=executor.submit(task);

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Cancel the task, finishing its execution
        System.out.printf("Main: Cancelling the Task\n");
        result.cancel(true);
        // Verify that the task has been cancelled
        System.out.printf("Main: Cancelled: %s\n",result.isCancelled());
        System.out.printf("Main: Done: %s\n",result.isDone());

        // Shutdown the executor
        executor.shutdown();
    }
}
