package com.zhaoyu.threads.ThreadPool.controlFinishATask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 使用FutureTask类对任务的完成进行一定的控制，如本例中，ResultTask继承FutureTask，并对done方法进行重写，任务在完成后
 * 会执行done方法。
 */
public class Main {
    public static void main(String[] args) {
        // Create an executor
        ExecutorService executor= Executors.newCachedThreadPool();

        //Create five tasks
        ResultTask resultTasks[]=new ResultTask[5];
        for (int i=0; i<5; i++) {
            ExecutableTask executableTask=new ExecutableTask("Task "+i);
            resultTasks[i]=new ResultTask(executableTask);
            executor.submit(resultTasks[i]);
        }

        // Sleep the thread five seconds
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        // 对所有的任务执行取消操作，但是已经执行完的任务不受影响。
        for (int i=0; i<resultTasks.length; i++) {
            resultTasks[i].cancel(true);
        }

        // 没有被取消的任务会打印
        for (int i=0; i<resultTasks.length; i++) {
            try {
                if (!resultTasks[i].isCancelled()){
                    System.out.printf("%s\n",resultTasks[i].get());
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();

    }
}
