package com.zhaoyu.threads.ThreadPool.completeService;

import java.util.concurrent.CompletionService;

public class ReportRequest implements Runnable {

    private final String name;

    /**
     * CompletionService 用于 ReportGenerator 任务的执行
     */
    private final CompletionService<String> service;

    public ReportRequest(String name, CompletionService<String> service){
        this.name=name;
        this.service=service;
    }

    /**
     * 创建 ReportGenerator 任务，通过 CompletionService执行。
     */
    @Override
    public void run() {
        ReportGenerator reportGenerator=new ReportGenerator(name, "Report");
        service.submit(reportGenerator);
    }

}