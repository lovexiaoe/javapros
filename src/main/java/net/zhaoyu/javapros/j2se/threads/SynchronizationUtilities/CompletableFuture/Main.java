package net.zhaoyu.javapros.j2se.threads.SynchronizationUtilities.CompletableFuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * java1.8提供了CompletableFuture类，我们使用一个复杂的任务流程说明其使用，CompletableFuture类实现了Future接口和CompletionStage接口。
 * 第一步，生成一个种子seed。
 * 第二步，利用种子生成一个 nunmber list
 * 第三步，使用 CompletableFuture.thenApplyAsync()生成3个并行的任务，都需要依赖number list：
 *          1，计算list中离1000最近的数。
 *          2，计算list中最大的数。
 *          3，计算list中最大数和最小数的平均值。
 * 第四步，等待第三步3个任务的结束。
 */
public class Main {
    public static void main(String[] args) {

        System.out.printf("TaskDelay: Start\n");
        CompletableFuture<Integer> seedFuture = new CompletableFuture<>();
        Thread seedThread = new Thread(new SeedGenerator(seedFuture));
        seedThread.start();

        System.out.printf("TaskDelay: Getting the seed\n");
        int seed = 0;
        try {
            seed = seedFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.printf("TaskDelay: The seed is: %d\n", seed);

        System.out.printf("TaskDelay: Launching the list of numbers generator\n");
        NumberListGenerator task = new NumberListGenerator(seed);

        /**
         * supplyAsync方法用一个Supplier接口作为参数，返回一个CompletableFuture。CompletableFuture的结果由在ForkJoinPool
         * 中的一个任务执行，这个任务返回静态方法commonPool()。
         */
        CompletableFuture<List<Long>> startFuture = CompletableFuture.supplyAsync(task);

        System.out.printf("TaskDelay: Launching step 1\n");  //第一步找出在list中离1000最近的数字。
        /**使用thenApplyAsync方法连接一些任务，使用Function接口的实现作为参数。被CompletableFuture生成的值将作为参数
         * 传给Function的实现，并执行，执行后得到另一个CompletableFuture
         */
        CompletableFuture<Long> step1Future = startFuture.thenApplyAsync(list -> {
            System.out.printf("%s: Step 1: Start\n", Thread.currentThread().getName());
            long selected = 0;
            long selectedDistance = Long.MAX_VALUE;
            long distance;
            for (Long number : list) {
                distance = Math.abs(number - 1000);
                if (distance < selectedDistance) {
                    selected = number;
                    selectedDistance = distance;
                }
            }
            System.out.printf("%s: Step 1: Result - %d\n", Thread.currentThread().getName(), selected);
            return selected;
        });

        System.out.printf("TaskDelay: Launching step 2\n"); //第二步找出在list中最大的数字。
        CompletableFuture<Long> step2Future = startFuture
                .thenApplyAsync(list -> list.stream().max(Long::compare).get());

        /**
         *  使用thenAccept接收step2Future的处理结果。
         */

        CompletableFuture<Void> write2Future = step2Future.thenAccept(selected -> {
            System.out.printf("%s: Step 2: Result - %d\n", Thread.currentThread().getName(), selected);
        });

        System.out.printf("TaskDelay: Launching step 3\n"); //第三步找出在list中最大数和最小数的平均值。
        NumberSelector numberSelector = new NumberSelector();
        CompletableFuture<Long> step3Future = startFuture.thenApplyAsync(numberSelector);

        System.out.printf("TaskDelay: Waiting for the end of the three steps\n");
        /**
         * allOf等待多个任务的执行结束。
         * 使用thenAcceptAsync同步接收waitFuture的执行结果。
         */
        CompletableFuture<Void> waitFuture = CompletableFuture.allOf(step1Future, write2Future, step3Future);
        CompletableFuture<Void> finalFuture = waitFuture.thenAcceptAsync((param) -> {
            System.out.printf("TaskDelay: The CompletableFuture example has been completed.");
        });

        //join将finalFuture加入主线程
        finalFuture.join();
    }
}
