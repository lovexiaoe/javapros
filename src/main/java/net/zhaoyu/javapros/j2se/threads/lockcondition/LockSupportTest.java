package net.zhaoyu.javapros.j2se.threads.lockcondition;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport类为线程的阻塞和解除阻塞提供支持，LockSupport为使用它的每个线程关联一个许可（permit）类似于permit为1的信号量，
 * 默认是不可用，调用一次unpark就变成可用，再次重复调用unpark permit状态不会变化。通过调用park消费permit,permit由可用变成不可用，
 * 再次调用park，线程会阻塞，直到permit变成可用。
 *
 * LockSupport是不可重入的，即一个permit只能允许一个线程消费一次，而不是允许一个线程所有的消费。
 */
public class LockSupportTest {

    /**
     * 在线程执行计划中，禁用当前线程，直到该线程的permit可用。
     *
     * 1，如果当前线程的permit状态可用，线程立即消费这个permit，并返回，设置permit状态不可用。
     * 2，如果当前线程的permit状态不可用，则阻塞(当前线程在线程执行计划中不可用，并休眠)。
     * 直到下面3个情况之一发生。
     * 1，其他线程unpark该线程。
     * 2，其他线程打断该线程。
     * 3，其他无缘由的调用return。
     *
     * @param blocker 为这次线程停顿负责的对象
     * @since 1.6
     */
    public static void park(Object blocker) {
    }

    /**
     * 1，如果当前线程的permit状态可用，则保持不变。
     * 2，如果当前线程的permit状态不可用，则设置为可用，如果当前线程正在park阻塞中，那么park停止阻塞继续执行，并消费这次permit状态
     * 如果当前线程没有处在park阻塞中，则等待下一个park消费permit状态。
     *
     * 对于还没start的线程不起作用。
     *
     * @param thread the thread to unpark, or {@code null}, in which case
     *        this operation has no effect
     */
    public static void unpark(Thread thread) {
    }

    /**
     * 主线程为thread，3秒后调用unpark获取permit，子线程thread1调用park时，由于没有permit，所以会阻塞，
     * 直到主线程获取permit，子线程thread1继续执行
     */
    public static void main(String[] args) {
        // 主线程两次unpark,子线程2两次park，但是子线程2晚执行。
        Thread thread2=new Thread(new Runnable() {
            @Override
            public void run() {
                Thread thread = Thread.currentThread();
                try {
                    thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LockSupport.park();//如果permit可用则立即执行，消费许可。
                System.out.println("线程2 执行结束");
                //LockSupport.park();//再次消费许可,permit不可用，线程会陷入阻塞,直到其他线程unpark该线程。
            }
        });

        thread2.start();
        LockSupport.unpark(thread2);//调用unpark。
        LockSupport.unpark(thread2);//重复调用unpark，permit状态不变。

        // 主线程unpark,子线程1执行park，但是主线程晚执行。
        Thread thread1=new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程1 开始第一次park");
                LockSupport.park();//默认permit状态为不可用，所以阻塞，直到主线程设置permit状态为可用。
                System.out.println("线程1 开始第二次park");
                LockSupport.park();//再次消费许可,主线程的permit状态被前一行设置为不可用，所以阻塞，直到主线程设置permit状态为可用。
                System.out.println("线程1 执行结束");
            }
        });
        thread1.start();
        Thread thread = Thread.currentThread();
        try {
            thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程main 3秒后调用 unpark thread1");
        LockSupport.unpark(thread1);//获取permit
        try {
            thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程main 3秒后再次调用 unpark thread1");
        LockSupport.unpark(thread1);//获取permit


        //park阻塞在线程interrupt后，会响应中断，结束park。
        Thread thread3=new Thread(new Runnable() {
            @Override
            public void run() {
                LockSupport.park();
                System.out.println("线程3运行结束，打断状态为："+Thread.currentThread().isInterrupted());

            }
        });
        thread3.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread3.interrupt(); //thread3打断，回响应中断，但是不会抛出InterruptedException
    }

}
