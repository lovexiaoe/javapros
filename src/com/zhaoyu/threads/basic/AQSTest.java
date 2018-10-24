package com.zhaoyu.threads.basic;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * AbstractQueueSynchronizer简称AQS,定义了一种等待队列的节点。AQS定义的等待队列是CLH 锁的一种变种。CLH锁一般用于
 * spinlocks。它的基本策略中，可以保存前驱节点关于一个线程的控制信息，基于这个策略，我们将他也用于blocking synchronizer。
 * 每个节点中的status字段跟踪线程是否应该阻塞。当一个节点的前驱节点释放时，该节点就会收到通知。队列中的每个节点作为一个
 * monitor保存一个等待中的线程 。status状态不能决定线程是否得到锁，在队列中的第一个线程会尝试获取锁，仅仅是让它去竞争，
 * 并不能确保获得锁。
 *
 * CLH队列锁时一种双向的FIFO队列。参考{@code CLHLock}
 *
 * AQS中的队列定义了一个FIFO的双向链表，并声明了Head和Tail。入队列时，在tail后面添加，出队列时，设置head指针为next。
 * 节点包括prev,next,thread,waitStatus(包括若干个状态，SIGNAL,CANCELED,CONDITION,PROPAGATE,0等，具体参考类中说明)，
 * nextWaiter：当waitStatus为CONDITION,或者为共享锁模式时，记录等待节点。
 *
 * 取消引入了一个问题，当一个节点的前驱或者后驱节点被取消时，我们会得不到通知消息。一直使用unpark处理继任节点，直到我们
 * 找到没有被取消的节点。
 *
 * AQS在第一次使用锁时，创建Head和Tail节点。
 *
 * 条件等待的线程使用相同的节点。但是会使用一个额外的队列存放。等待条件只需要使用非并发的队列存放，因为他们只在获得排它
 * 锁之后访问。await后，向条件队列中插入一个节点，signal后，相关节点转移到主队列中。用一个特殊的状态字段标记节点所在的队列。
 *
 *
 */
public class AQSTest {

    /**
     * 改方法用来实现Lock()方法。
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
    //下面是公平锁的trAcquire实现。 hasQueuedPredecessors方法判断AQS队列不为空且在当前线程节点之前是有等待节点，不满足，则
    //说明满足公平所的要求，进行下面的步骤，反之，非公平锁则不需要进这样的判断。
    //简单来说，非公平锁可以随便enqueue,而公平所必须等待前面的执行完成，才轮到当前线程。
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
