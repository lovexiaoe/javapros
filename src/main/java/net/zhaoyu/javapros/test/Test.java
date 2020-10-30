package net.zhaoyu.javapros.test;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

//用于自己临时调试一些程序。
public class Test {


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

