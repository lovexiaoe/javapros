package net.zhaoyu.javapros.j2se.threads.SynchronizationUtilities.CountDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * 这个类实现一个视频会议的控制
 *
 * 使用CountDownlatch 控制会议参与者的到达，所有参会者到达后开启会议。
 *
 */
public class Videoconference implements Runnable{

    private final CountDownLatch controller;

    /**
     * 参数number控制参会者的数量。
     */
    public Videoconference(int number) {
        controller=new CountDownLatch(number);
    }

    /**
     * 每个参会者到达时调用该方法，CountDownLatch变化计数。
     */
    public void arrive(String name){
        System.out.printf("%s 到达.\n",name);
        controller.countDown();
        System.out.printf("视频会议: 还需等待 %d 个参会者.\n",controller.getCount());
    }

    /**
     * 视频会议控制的主方法，等待所有的参会者到达，然后开启会议。
     */
    @Override
    public void run() {
        System.out.printf("视频会议: 初始化: 共%d 个参会者.\n",controller.getCount());
        try {
            // Wait for all the participants
            controller.await();
            // Starts the conference
            System.out.printf("视频会议: 所有参会者到达\n");
            System.out.printf("视频会议: 开始会议...\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
