package com.zhaoyu.threads.SynchronizationUtilities.CyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 从矩阵的若干行查找数字。
 */
public class Searcher implements Runnable{
    /**
     * First row where look for
     */
    private final int firstRow;

    /**
     * Last row where look for
     */
    private final int lastRow;

    /**
     * Bi-dimensional array with the numbers
     */
    private final MatrixMock mock;

    /**
     * Array to store the results
     */
    private final Results results;

    /**
     * Number to look for
     */
    private final int number;

    /**
     * CyclicBarrier to control the execution
     */
    private final CyclicBarrier barrier;

    /**
     * @param firstRow First row where look for
     * @param lastRow Last row where fook for
     * @param mock Object with the array of numbers
     * @param results Array to store the results
     * @param number Number to look for
     * @param barrier CyclicBarrier to control the execution
     */
    public Searcher(int firstRow, int lastRow, MatrixMock mock, Results results, int number, CyclicBarrier barrier){
        this.firstRow=firstRow;
        this.lastRow=lastRow;
        this.mock=mock;
        this.results=results;
        this.number=number;
        this.barrier=barrier;
    }

    /**
     * TaskDelay method of the searcher. Look for the number in a subset of rows. For each row, saves the
     * number of occurrences of the number in the array of results
     */
    @Override
    public void run() {
        int counter;
        System.out.printf("%s: 行查找 从第 %d 行到第 %d 行.\n",Thread.currentThread().getName(),firstRow,lastRow);
        for (int i=firstRow; i<lastRow; i++){
            int row[]=mock.getRow(i);
            counter=0;
            for (int j=0; j<row.length; j++){
                if (row[j]==number){
                    counter++;
                }
            }
            results.setData(i, counter);
        }
        System.out.printf("%s: 行查找结束.\n",Thread.currentThread().getName());
        try {
            System.out.printf("%s: 到达barrier.\n",Thread.currentThread().getName());
            barrier.await();
            System.out.printf("%s: 已经越过barrier.\n",Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
