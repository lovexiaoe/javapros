package net.zhaoyu.javapros.j2se.threads.SynchronizationUtilities.Phaser.Control;

import java.util.Date;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * 模拟一个考试的学生，需要到达并完成两轮测试，共3个阶段。
 *
 */
public class Student implements Runnable {

    /**
     * Phaser to control the execution
     */
    private Phaser phaser;

    /**
     * Constructor of the class. Initialize its objects
     * @param phaser Phaser to control the execution
     */
    public Student(Phaser phaser) {
        this.phaser=phaser;
    }

    /**
     * TaskDelay method of the student. It arrives to the exam and does three exercises. After each
     * exercise, it calls the phaser to wait that all the students finishes the same exercise
     */
    public void run() {
        System.out.printf("%s: Has arrived to do the exam. %s\n",Thread.currentThread().getName(),new Date());
        phaser.arriveAndAwaitAdvance();
        System.out.printf("%s: Is going to do the first exercise. %s\n",Thread.currentThread().getName(),new Date());
        doExercise();
        System.out.printf("%s: Has done the first exercise. %s\n",Thread.currentThread().getName(),new Date());
        phaser.arriveAndAwaitAdvance();
        System.out.printf("%s: Is going to do the second exercise. %s\n",Thread.currentThread().getName(),new Date());
        doExercise();
        System.out.printf("%s: Has finished the exam. %s\n",Thread.currentThread().getName(),new Date());
        phaser.arriveAndAwaitAdvance();
    }

    /**
     * Does an exercise is to wait a random time
     */
    private void doExercise() {
        try {
            Long duration=(long)(Math.random()*10);
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}