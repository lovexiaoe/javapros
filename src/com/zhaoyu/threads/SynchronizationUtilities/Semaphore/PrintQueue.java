package com.zhaoyu.threads.SynchronizationUtilities.Semaphore;

import java.util.Date;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 该例子用于演示信号量的使用，信号量设定一定的数量，用户控制线程，或者其他程序的运行。
 * 如果获取超过一定数量，则会阻塞等待。
 */
public class PrintQueue {
    private final Semaphore semaphore;
    private final boolean freePrinters[];
    private final Lock lockPrinters;
    private AtomicInteger count=new AtomicInteger(0);
    public PrintQueue(){
        semaphore=new Semaphore(3);
        freePrinters=new boolean[3];
        for (int i=0; i<3; i++){
            freePrinters[i]=true;
        }
        lockPrinters=new ReentrantLock();
    }

    public void printJob (Object document){
        try {
            semaphore.acquire();
            System.out.println("获得一个信号量,总信号量为： "+count.addAndGet(1));
            int assignedPrinter=getPrinter();
            long duration=(long)(Math.random()*10);
            System.out.printf("%s - %s: PrintQueue: Printing a Job in Printer %d during %d seconds\n",
            new Date(), Thread.currentThread().getName(),
                    assignedPrinter,duration);
            TimeUnit.SECONDS.sleep(duration);
            freePrinters[assignedPrinter]=true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("释放一个信号量,总信号量为： "+count.decrementAndGet());
            semaphore.release();
        }
    }

    private int getPrinter() {
        int ret=-1;
        try {
            lockPrinters.lock();
            for (int i=0; i<freePrinters.length; i++) {
                if (freePrinters[i]){
                    ret=i;
                    freePrinters[i]=false;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lockPrinters.unlock();
        }
        return ret;
    }
}
