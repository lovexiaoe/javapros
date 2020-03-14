package net.zhaoyu.javapros.j2se.threads.ThreadPool.createAndRejected;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Task implements Runnable {

    private final Date initDate;

    private final String name;

    public Task(String name){
        initDate=new Date();
        this.name=name;
    }

    /**
     * task sleep some time
     */
    @Override
    public void run() {
        System.out.printf("%s: %s: Created and start on: %s\n",Thread.currentThread().getName(),name,initDate);
        try {
            Long duration=(long)(Math.random()*10);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("%s: %s: Finished on: %s\n",Thread.currentThread().getName(),name,new Date());
    }

}
