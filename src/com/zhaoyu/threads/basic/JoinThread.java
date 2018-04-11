package com.zhaoyu.threads.basic;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JoinThread {
    public static void main(String[] args) {
        DataSourcesLoader dsLoader = new DataSourcesLoader();
        Thread thread1 = new Thread(dsLoader,"DataSourceThread");
        DataSourcesLoader ncLoader = new DataSourcesLoader();
        Thread thread2 = new Thread(ncLoader,"NetworkConnectionLoader");
        thread1.start();
        thread2.start();
        try {
            //join方法等待该线程执行结束。
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("TaskDelay: Configuration has been loaded: %s\n",
                new Date());
    }
}

class DataSourcesLoader implements Runnable {
    @Override
    public void run() {
        System.out.printf("Beginning data sources loading: %s\n",
                new Date());
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Data sources loading has finished: %s\n",
                new Date());
    }
}