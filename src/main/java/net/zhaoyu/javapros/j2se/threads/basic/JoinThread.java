package net.zhaoyu.javapros.j2se.threads.basic;

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
        //给执行计划器发出自己退让的信号，如果计划器是空闲的，那么忽略该信号。其他和该线程同优先级的线程会抢占执行。
        // 在程序非常少用，一般用户调试，检测race condition等。
        Thread.yield();
        try {
            //join方法让当前线程等待该线程执行结束，如下，主线程会分别等待thread1和thread2执行完成后继续。
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