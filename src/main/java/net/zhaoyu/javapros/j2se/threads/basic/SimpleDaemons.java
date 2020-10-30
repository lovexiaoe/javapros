package net.zhaoyu.javapros.j2se.threads.basic;

import java.util.concurrent.TimeUnit;

/**
 * 通过setDaemon方法可以将用户线程设置成守护线程。守护线程服务于用户线程。
 * 主线程结束后，用户线程继续运行。
 * 如果没有用户线程，只有守护线程，那么JVM结束。垃圾回收就是典型的守护线程。
 *
 * 守护线程应该永远不要去访问固有资源 ，如数据库，文件。
 */
public class SimpleDaemons implements Runnable{
    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println(Thread.currentThread()+" "+this);
            } catch (InterruptedException e) {
                System.out.println("sleep() interrupted");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Thread daemon = new Thread(new SimpleDaemons());
            //如果将线程都设置为守护线程，那么在主线程结束后，没有用户线程运行，守护线程也会结束，JVM退出。
            daemon.setDaemon(true);
            daemon.start();
        }
        System.out.println("All Daemons started.");
        TimeUnit.MILLISECONDS.sleep(175);
    }
}
