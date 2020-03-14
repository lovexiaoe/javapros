package net.zhaoyu.javapros.j2se.threads.ThreadPool.completeService;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ReportProcessor implements Runnable {

    /**
     * CompletionService 用于 ReportGenerator 任务的执行
     */
    private final CompletionService<String> service;

    private volatile boolean end;

    public ReportProcessor (CompletionService<String> service){
        this.service=service;
        end=false;
    }

    /**
     * 当end为false时, 这个方法调用CompletionService的poll方法并等待20秒。
     * 直到end状态为true。
     */
    @Override
    public void run() {
        while (!end){
            try {
                Future<String> result=service.poll(20, TimeUnit.SECONDS);
                if (result!=null) {
                    String report=result.get();
                    System.out.printf("ReportProcessor: Report Recived: %s\n",report);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        System.out.printf("ReportProcessor: End\n");
    }

    /**
     * 设置end属性为true
     */
    public void stopProcessing() {
        this.end = true;
    }
}