package net.zhaoyu.javapros.j2se.threads.lockcondition.customlock;

import java.util.concurrent.TimeUnit;

/**
 * @Description: 演示自定义的临界区的实现。
 * @Author: zhaoyu
 * @Date: 2020/10/23
 */
public class Main {
    public static void main(String[] args) {
        MyLock lock=new MyLock();
        for (int i = 0; i < 10; i++) {
            Task task=new Task(lock,"Task-"+i);
            Thread thread=new Thread(task);
            thread.start();
        }

        boolean flag;
        do {
            try {
                flag = lock.tryLock(1, TimeUnit.SECONDS);
                if (!flag) {
                    System.out.printf("Main: Trying to get the Lock\n");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                flag = false;
            }
        } while (!flag);
        System.out.printf("Main: Got the lock\n");
        lock.unlock();
    }
}
