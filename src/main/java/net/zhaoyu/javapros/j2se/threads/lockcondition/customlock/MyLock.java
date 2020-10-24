package net.zhaoyu.javapros.j2se.threads.lockcondition.customlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Description: 自定义一个锁，实现简单的临界区。
 * @Author: zhaoyu
 * @Date: 2020/10/23
 */
public class MyLock implements Lock {

    private final AbstractQueuedSynchronizer sync;

    public MyLock() {
        this.sync = new MyAbstractQueuedSynchronizer();
    }

    @Override
    public void lock() {
        //arg可以是任意的值
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        try {
            return sync.tryAcquireNanos(1, 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1,TimeUnit.NANOSECONDS.convert(time,unit));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.new ConditionObject();
    }
}
