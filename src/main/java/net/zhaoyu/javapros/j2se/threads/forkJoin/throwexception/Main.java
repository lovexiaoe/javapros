package net.zhaoyu.javapros.j2se.threads.forkJoin.throwexception;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * 在ForkJoinPool中complete方法中不能抛出检测异常，因为在他的实现中没有包含throws声明。
 * 你必须编写必要的代码去处理检测异常，然而你可以抛出未检测异常。
 * 但是forkJoinPool和ForkJoinPool和你期盼的不一样。程序不会因为异常停止，你也看不到任何在控制台中的异常信息。它好像
 * 吞掉了异常一样。只有当你初始化任务时调用get()方法，异常才会抛出。
 * 然而你可以使用ForkJoinTask中的一些方法得到异常信息。
 */
public class Main {
    public static void main(String[] args) {
        // Array of 100 integers
        int array[]=new int[100];
        // Task to process the array
        Task task=new Task(array,0,100);
        // ForkJoinPool to execute the Task
        ForkJoinPool pool=new ForkJoinPool();

        // Execute the task
        pool.execute(task);

        // Shutdown the ForkJoinPool
        pool.shutdown();

        // Wait for the finalization of the task
        try {
            pool.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 检查任务是否正常结束，如果不是则抛出异常。

        if (task.isCompletedAbnormally()) {
            System.out.printf("Main: An exception has ocurred\n");
            System.out.printf("Main: %s\n",task.getException());
        }

        System.out.printf("Main: Result: %d",task.join());
    }
}
