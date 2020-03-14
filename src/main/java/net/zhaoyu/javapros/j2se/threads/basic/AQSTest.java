package net.zhaoyu.javapros.j2se.threads.basic;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * AbstractQueueSynchronizer简称AQS,定义了一种等待队列的节点。AQS定义的等待队列是CLH 锁的一种变种。CLH锁一般用于
 * spinlocks。它的基本策略中，可以保存前驱节点关于一个线程的控制信息，基于这个策略，我们将他也用于blocking synchronizer。
 * 每个节点中的status字段跟踪线程是否应该阻塞。当一个节点的前驱节点释放时，该节点就会收到通知。否则，每个节点都会持有一个
 * 线程，作为等待通知的监视器等待通知。status状态不能决定线程是否得到锁，在队列中的第一个线程会尝试获取锁，仅仅是让它去竞争，
 * 并不能确保获得锁。
 *
 * CLH队列锁是一种双向的FIFO队列。参考{@code CLHLock}
 *
 * AQS中的队列定义了一个FIFO的双向链表，并声明了Head和Tail。入队列时，在tail后面添加，出队列时，设置head指针为next。
 * 节点包括prev,next,thread,waitStatus(包括若干个状态，SIGNAL,CANCELED,CONDITION,PROPAGATE,0等，具体参考类中说明)，
 * nextWaiter：当waitStatus为CONDITION,或者为共享锁模式时，记录等待节点。
 *
 *
 */
public class AQSTest {

    /**
     * 该方法用来实现Lock()方法。
     * 在exclusive 模式下，忽略interrupt, AQS通过acquire方法获取锁，而acquire通过tryAcquire和acquireQueued方法实现。
     * 先调用AQS子类重写的tryAcquire方法，如果获取锁成功，直接返回。如果失败，线程enqueue到队列，对刚添加的线程节点，
     * 不断地用blocking和unblocking调用 tryAcquire，直到成功。
     *
     */
    public void acquire(int arg){
        //if (!tryAcquire(arg) &&acquireQueued(addWaiter(AbstractQueuedSynchronizer.Node.EXCLUSIVE), arg))
        //selfInterrupt();
    }

    //排它锁的具体定义，通过操作AQS中的变量实现。在AQS的子类中实现，AQS本身不实现。
    protected boolean tryAcquire(int arg) {
        throw new UnsupportedOperationException();
    }
    //下面是公平锁的trAcquire实现。 hasQueuedPredecessors方法判断AQS队列前驱节点中有没有其他的线程。
    // 如果有，则获取锁失败，如果没有，那么表示head就是当前队列，获取锁成功，这样便是公平锁。
    //非公平锁则不需要进这样的判断。
    //简单来说，非公平锁竞争执行,而公平所必须等待前面的执行完成，才轮到当前线程。
    protected final boolean tryAcquire(int acquires) {
        final Thread current = Thread.currentThread();
        int c = getState();
        if (c == 0) {
            if (!hasQueuedPredecessors() &&
                    compareAndSetState(0, acquires)) {
                setExclusiveOwnerThread(current);
                return true;
            }
        }
        else if (current == getExclusiveOwnerThread()) {
            int nextc = c + acquires;
            if (nextc < 0)
                throw new Error("Maximum lock count exceeded");
            setState(nextc);
            return true;
        }
        return false;
    }

    /**
     * 在exclusive uninterruptible模式下为已经在队列中的线程获取锁。检查node.pred的状态，判断是否需要blocking，
     * blocking过后再尝试调用tryAcquire。block是通过LockSupport.park()和LockSupport.unpark()实现。
     */
    //final boolean acquireQueued(final AbstractQueuedSynchronizer.Node node, int arg) {}

}
