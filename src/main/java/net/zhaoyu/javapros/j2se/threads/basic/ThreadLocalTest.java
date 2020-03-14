package net.zhaoyu.javapros.j2se.threads.basic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * ThreadLocal为每个线程提供属于他们自己的变量拷贝，ThreadLocal通常是private static的，并和线程关联的字段，如userId或者事务ID。
 * 只要线程存活，每个线程隐藏地持有一个自己变量拷贝的引用。ThreadLocalMap的Entry使用WeakReference实现，所以线程死亡后，线程的
 * 变量拷贝会被GC。
 *
 * ThreadLocal使用ThreadLocalMap实现，ThreadLocalMap是一个Entry数组,实现原理和HashMap类似，T
 * hreadLocalMap拥有一个自增长的threadLocalHashCode（涨幅默认为0x61c88647），
 * get，set等方法类似HashMap，通过对threadLocalHashCode对数组取模得到下标。
 *
 * 使用案例如：CLH队列锁中的node变量使用ThreadLocal，为每个线程创建一个节点。Servlet为每个
 * request请求下的一些变量存储。
 */


public class ThreadLocalTest {
    // 使用 AtomicInteger提供一个自增长Id
    private static final AtomicInteger nextId = new AtomicInteger(0);

    // 定义ThreadLocal变量，每个线程拥有一个Id
    private static final ThreadLocal<Integer> threadId =
            new ThreadLocal<Integer>() {
                @Override
                protected Integer initialValue() {
                    return nextId.getAndIncrement();
                }
            };

    // 返回当前线程的Id
    public static int get() {
        return threadId.get();
    }
}
