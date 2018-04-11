package com.zhaoyu.threads.ThreadPool.executeAndReturnAResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Executor 使用submit接收Callable参数，将结果返回成一个Future。Future可以异步得到线程的执行结果。
 */
public class Main {
    public static void main(String[] args) {

        ThreadPoolExecutor executor=(ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        // 一个Future的列表，存放计算结果
        List<Future<Integer>> resultList=new ArrayList<>();

        // Create a random number generator
        Random random=new Random();
        // Create and send to the executor the ten tasks
        for (int i=0; i<10; i++){
            Integer number=random.nextInt(10);
            FactorialCalculator calculator=new FactorialCalculator(number);
            Future<Integer> result=executor.submit(calculator);
            resultList.add(result);
        }

        // 输出结果 results
        System.out.printf("TaskDelay: Results\n");
        for (int i=0; i<resultList.size(); i++) {
            Future<Integer> result=resultList.get(i);
            Integer number=null;
            try {
                number=result.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            System.out.printf("Core: TaskPeriodically %d: %d\n",i,number);
        }

        // Shutdown the executor
        executor.shutdown();
    }
}
