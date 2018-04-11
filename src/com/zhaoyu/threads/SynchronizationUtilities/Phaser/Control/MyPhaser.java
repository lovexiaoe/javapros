package com.zhaoyu.threads.SynchronizationUtilities.Phaser.Control;

import java.util.concurrent.Phaser;

/**
 * 自定义一个Phaser，重写onAdvance 方法
 *
 */
public class MyPhaser extends Phaser {

    /**
     * 当所有注册的线程调用其中一个advance方法时，该方法被调用

     * @param phase Actual phase
     * @param registeredParties Number of registered threads
     * @return false to advance the phase, true to finish
     */
    @Override
    protected boolean onAdvance(int phase, int registeredParties) {
        switch (phase) {
            case 0:
                return studentsArrived();
            case 1:
                return finishFirstExercise();
            case 2:
                return finishExam();
            default:
                return true;
        }
    }

    /**
     * 从阶段0到阶段1时调用该方法。
     * @return false to continue with the execution
     */
    private boolean studentsArrived() {
        System.out.printf("Phaser: The exam are going to start. The students are ready.\n");
        System.out.printf("Phaser: We have %d students.\n",getRegisteredParties());
        return false;
    }

    /**
     * 从阶段1到阶段2时调用该方法。
     * @return false to continue with the execution
     */
    private boolean finishFirstExercise() {
        System.out.printf("Phaser: All the students has finished the first exercise.\n");
        System.out.printf("Phaser: It's turn for the second one.\n");
        return false;
    }


    /**
     * 从阶段2到阶段3时调用该方法。
     * @return true
     */
    private boolean finishExam() {
        System.out.printf("Phaser: All the students has finished the exam.\n");
        System.out.printf("Phaser: Thank you for your time.\n");
        return true;
    }

}