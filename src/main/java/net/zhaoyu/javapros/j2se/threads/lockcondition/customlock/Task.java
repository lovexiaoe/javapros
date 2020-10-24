package net.zhaoyu.javapros.j2se.threads.lockcondition.customlock;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: zhaoyu
 * @Date: 2020/10/23
 */
public class Task implements Runnable{
    private final MyLock lock;
    private final String name;

    public Task(MyLock lock, String name) {
        this.lock = lock;
        this.name = name;
    }

    @Override
    public void run() {
        lock.lock();
        System.out.printf("Task: %s: Take the lock\n",name);
        try {
            TimeUnit.SECONDS.sleep(2);
            System.out.printf("Task: %s: Free the lock\n",name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
