package net.zhaoyu.javapros.j2se.threads.SynchronizationUtilities.CyclicBarrier;

import java.util.concurrent.CyclicBarrier;

/**
 *
 *
 * cyclicBarrier和countDownLatch类似，通过一个ReentrantLock和ReentrantLock.newCondition 实现，和CountDownLatch相比，
 * CountDownLatch是一组线程等待一组动作结束，动作之间不会等待，而cyclicBarrier强调互相等待，在定义一定数量的等待线程后，
 * 在调用await()后，线程会等待其他线程到达await，当所有线程都到达后，线程继续执行。
 *
 * cyclicBarrier会出现一个特殊状态，Broken状态，当await中的一个线程interrupted的时候，得到InterruptedException，那么其他
 * 的线程会得到BrokenBarrierException，这时cyclicBarrier的状态为broken状态。
 */
public class Main {
    public static void main(String[] args) {

        final int ROWS=10000;               //矩阵的行数
        final int NUMBERS=1000;              //举证的列数
        final int SEARCH=5;                   //查找的数字
        final int PARTICIPANTS=5;             //启动的线程数
        final int LINES_PARTICIPANT=2000;       //每个线程运行的行数
        MatrixMock mock=new MatrixMock(ROWS, NUMBERS,SEARCH);

        Results results=new Results(ROWS);

        // Creates an Grouper object
        Grouper grouper=new Grouper(results);

        // 创建一个5个参与者的栅栏，等待5个参与者执行完后，执行grouper线程。
        CyclicBarrier barrier=new CyclicBarrier(PARTICIPANTS,grouper);

        // Creates, initializes and starts 5 Searcher objects
        Searcher searchers[]=new Searcher[PARTICIPANTS];
        for (int i=0; i<PARTICIPANTS; i++){
            searchers[i]=new Searcher(i*LINES_PARTICIPANT, (i*LINES_PARTICIPANT)+LINES_PARTICIPANT, mock, results, 5,barrier);
            Thread thread=new Thread(searchers[i]);
            thread.start();
        }
        System.out.printf("TaskDelay: 主程序运行完成.\n");
    }
}
