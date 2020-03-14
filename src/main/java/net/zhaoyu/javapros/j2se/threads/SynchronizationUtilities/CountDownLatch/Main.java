package net.zhaoyu.javapros.j2se.threads.SynchronizationUtilities.CountDownLatch;

/**
 * Latch-门栓的意思，CountDownLatch用于等待多个并发时间完成，和golang的WaitGroup类似。可以让一个线程等待多个线程完成
 * 若干个动作，或者一个动作完成若干次。
 *
 * CountDownLatch使用AQS的shared 模式实现,所以可以添加多个await()会议，当所有参会者到来时，多个会议都会进行。
 * 和CyclicBarrier相比，CountDownLatch是某个或多个线程等待一批任务线程的完成，在任务线程中计数，直到所有任务线程执行完，
 * 等待线程继续执行。由于是共享锁，等待线程可以设置多个。计数器归0后，多个等待线程都可以执行。
 */
public class Main {

    public static void main(String[] args) {

        //视频会议线程，开始运行
        Videoconference conference=new Videoconference(10);
        Thread threadConference=new Thread(conference);
        threadConference.start();

        //第二个视屏会议。
        Thread threadConference1=new Thread(conference);
        threadConference1.start();

        // 参会者线程开启
        for (int i=0; i<10; i++){
            Participant p=new Participant(conference, "参会者 "+i);
            Thread t=new Thread(p);
            t.start();
        }

    }

}