package net.zhaoyu.javapros.j2se.threads.SynchronizationUtilities.CountDownLatch;

import java.util.concurrent.TimeUnit;

/**
 * 表示一个视频会的参会者。
 */
public class Participant implements Runnable {

    /**
     * 参会者需要参加的视频会议
     */
    private Videoconference conference;

    /**
     * 参会者的名称
     */
    private String name;

    public Participant(Videoconference conference, String name) {
        this.conference=conference;
        this.name=name;
    }

    /**
     * 参会者在一段时间后到达，参加会议。
     */
    @Override
    public void run() {
        Long duration=(long)(Math.random()*10);
        try {
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        conference.arrive(name);
    }
}