package net.zhaoyu.javapros.j2se.threads.ThreadPool.completeService;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 该类模仿一个报表。
 */
public class ReportGenerator implements Callable<String> {

    /**
     * The sender of the report
     */
    private final String sender;
    /**
     * The title of the report
     */
    private final String title;

    public ReportGenerator(String sender, String title){
        this.sender=sender;
        this.title=title;
    }

    @Override
    public String call() throws Exception {
        try {
            Long duration=(long)(Math.random()*10);
            System.out.printf("%s_%s: ReportGenerator: Generating a report during %d seconds\n",this.sender,this.title,duration);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String ret=sender+": "+title;
        return ret;
    }

}