package net.zhaoyu.javapros.j2se.threads.basic.threadproperties;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 该例打印线程在运行时的状态变化。
 * NEW:线程被创建，但是还没有调用start前。
 * RUNNABLE：线程被JVM执行。在OS层级可能没有运行，如在等待cpu资源。
 * BLOCKED：线程被阻塞，等待一个monitor（synchronized），即线程还没有获得锁。
 * WAITING：线程已经获得了锁，但是需要等待其他线程进行某些操作，调用wait,join,park方法后进入waiting状态。
 * TIMED_WAITING：和WAITING类似，调用了有时限的wait,join,park方法后进入该状态。
 * TERMINATED：线程完成执行。
 */
public class ThreadStates {
    public static void main(String[] args) {
        Thread threads[];
        Thread.State status[];

        threads=new Thread[10];
        status = new Thread.State[10];

        //启动10个线程，5个最大优先级，5个最小优先级
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(new Calculator());
            if ((i % 2) == 0) {
                threads[i].setPriority(Thread.MAX_PRIORITY);
            } else {
                threads[i].setPriority(Thread.MIN_PRIORITY);
            }
            threads[i].setName("线程"+i);
        }


        try (FileWriter file = new FileWriter("log.txt"); PrintWriter pw = new PrintWriter(file);) {

            // 写入线程的初始状态。
            for (int i = 0; i < 10; i++) {
                pw.println("Main : Status of Thread " + i + " : " + threads[i].getState());
                status[i] = threads[i].getState();
            }

            // 启动所有线程
            for (int i = 0; i < 10; i++) {
                threads[i].start();
            }

            // 在线程没结束前，如果有线程状态改变了，打印线程的状态。直到所有线程结束。
            boolean finish = false;
            while (!finish) {
                for (int i = 0; i < 10; i++) {
                    if (threads[i].getState() != status[i]) {
                        writeThreadInfo(pw, threads[i], status[i]);
                        status[i] = threads[i].getState();
                    }
                }

                finish = true;
                //计算所有线程是否结束。
                for (int i = 0; i < 10; i++) {
                    finish = finish && (threads[i].getState() == Thread.State.TERMINATED);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeThreadInfo(PrintWriter pw,
                                        Thread thread,
                                        Thread.State state) {
        pw.printf("Main : Id %d - %s\n", thread.getId(), thread.getName());
        pw.printf("Main : Priority: %d\n", thread.getPriority());
        pw.printf("Main : Old State: %s\n", state);
        pw.printf("Main : New State: %s\n", thread.getState());
        pw.printf("Main : ************************************\n");
    }
}

