package net.zhaoyu.javapros.j2se.threads.lockcondition.customlock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @Description: 自定义AQS,定义一个临界区，state用于控制锁的访问
 * @Author: zhaoyu
 * @Date: 2020/10/23
 */
public class MyAbstractQueuedSynchronizer extends AbstractQueuedSynchronizer {
    private final AtomicInteger state;

    public MyAbstractQueuedSynchronizer() {
        this.state = new AtomicInteger(0);
    }

    @Override
    protected boolean tryAcquire(int arg) {
        return state.compareAndSet(0, 1);
    }

    @Override
    protected boolean tryRelease(int arg) {
        return state.compareAndSet(1, 0);
    }
}
