package net.zhaoyu.javapros.j2se.threads.forkJoin.cancleatask;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * 在一个数组中查找一个数字出现的位置。主要说明如何取消任务。
 * cancel方法允许取消一个还没有执行的任务，如果已经开始执行了，调用cancel则不影响。
 */
public class Main {
    public static void main(String[] args) {

        // Generate an array of 1000 integers
        ArrayGenerator generator=new ArrayGenerator();
        int array[]=generator.generateArray(1000);

        // Create a TaskManager object
        TaskManager manager=new TaskManager();

        // Create a ForkJoinPool with the default constructor
        ForkJoinPool pool=new ForkJoinPool();

        // Create a Task to process the array
        SearchNumberTask task=new SearchNumberTask(array,0,1000,5,manager);

        // Execute the task
        pool.execute(task);

        // Shutdown the pool
        pool.shutdown();


        // Wait for the finalization of the task
        try {
            pool.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Write a message to indicate the end of the program
        System.out.printf("Main: The program has finished\n");
    }
}
