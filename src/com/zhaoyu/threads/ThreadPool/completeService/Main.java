package com.zhaoyu.threads.ThreadPool.completeService;

import java.util.concurrent.*;

/**
 * 本例主要说明CompletionService的使用，CompletionService有一个发送任务给一个executor的submit方法，和一个能够得到已经执行
 * 完的Future对象的poll方法。
 *
 * CompletionService主要使用场景：在一个对象（ReportRequest）中将任务放入executor,而在另一个对象(ReportProcessor)中获取执行的结果。
 * CompletionService持有一个queue，执行完成的Future放入queue中，方法poll从queue中拉取Future对象，如果queue为空，返回null,
 * 否则返回第一个元素，并从queue中删除。
 *
 * 而take方法和poll方法类似，但是会阻塞拉取Future对象。
 */
public class Main {
    public static void main(String[] args) {
        ExecutorService executor= Executors.newCachedThreadPool();
        CompletionService<String> service=new ExecutorCompletionService<>(executor);

        // Crete two ReportRequest objects and two Threads to execute them
        ReportRequest faceRequest=new ReportRequest("Face", service);
        ReportRequest onlineRequest=new ReportRequest("Online", service);
        Thread faceThread=new Thread(faceRequest);
        Thread onlineThread=new Thread(onlineRequest);

        // Create a ReportSender object and a Thread to execute  it
        ReportProcessor processor=new ReportProcessor(service);
        Thread senderThread=new Thread(processor);

        // Start the Threads
        System.out.printf("Main: Starting the Threads\n");
        faceThread.start();
        onlineThread.start();
        senderThread.start();

        // Wait for the end of the ReportGenerator tasks
        try {
            System.out.printf("Main: Waiting for the report generators.\n");
            faceThread.join();
            onlineThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Shutdown the executor
        System.out.printf("Main: Shuting down the executor.\n");
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // End the execution of the ReportSender
        processor.stopProcessing();
        System.out.printf("Main: Ends\n");

    }
}
