package com.zhaoyu.threads.SynchronizationUtilities.Phaser;

import java.util.concurrent.Phaser;

/**
 *  演示Phaser阶段锁的使用，phaser将一个任务分为若干段，多个线程在每段共同完成后，再进入下一阶段。所以，当线程都到达第一阶段时，
 *  线程才可以进行第二阶段。
 *
 *  phaser有两个状态，1：Active,2,termination:当所有的paticipants都deregistered时，就是0个paticipants时。
 *
 *  和其他并发工具不同，phaser不需要控制任何异常，phaser中的sleeping线程不响应interruption事件，并不会抛出InterruptionException，
 *  除了一个例外，
 */
public class Main {
    public static void main(String[] args) {
        /**
         * 参数parties定义了participants的数量,即同时执行几个线程，超过的线程不在phaser的管理中，如果需要增加，则使用
         */

        Phaser phaser=new Phaser(4);

        // Creates 3 FileSearch objects. Each of them search in different directory
        FileSearch system=new FileSearch("C:\\Windows", "log", phaser);
        FileSearch apps=new FileSearch("C:\\Program Files","log",phaser);
        FileSearch documents=new FileSearch("C:\\Documents And Settings","log",phaser);
        FileSearch go=new FileSearch("C:\\Go","log",phaser);

        // Creates a thread to run the system FileSearch and starts it
        Thread systemThread=new Thread(system,"System");
        systemThread.start();

        // Creates a thread to run the apps FileSearch and starts it
        Thread appsThread=new Thread(apps,"Apps");
        appsThread.start();

        // Creates a thread to run the documents  FileSearch and starts it
        Thread documentsThread=new Thread(documents,"Documents");
        documentsThread.start();

        // Creates a thread to run the go  FileSearch and starts it
        Thread goThread=new Thread(go,"Go");
        goThread.start();
        try {
            systemThread.join();
            appsThread.join();
            documentsThread.join();
            goThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Terminated: %s\n",phaser.isTerminated());
    }
}
