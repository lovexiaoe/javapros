package net.zhaoyu.javapros.j2se.threads.basic;

import sun.misc.Unsafe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractOwnableSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;

/**
 * AbstractQueueSynchronizer简称AQS,定义了一种等待队列的节点。AQS定义的等待队列是CLH 锁的一种变种。
 *
 * AQS基于FIFO等待队列提供一个实现阻塞锁和相关同步器（栅栏，事件等）的框架，
 * 该类可以用来实现大多数依赖某个单一原子状态的同步器，将这个单一原子状态存放在队列的state中。
 * 子类必须定义protected方法改变这个原子状态，并且定义在这个对象被获取或者
 * 释放时，该状态的意义。类中的其他方法执行入队出队和阻塞机制。
 *
 * AQS定义了共享锁和排他锁的设置，并定义了条件队列用于实现锁的Condition。
 * AQS的公平锁和非公平锁是在子类中实现。两种都需要假如队列，只不过非公平锁是先尝试获取锁，获取失败则假如队列。公平锁直接假如队列。
 *
 * 共享模式，共享节点的后续节点都是{@link Node#SHARED},可以判断是否是共享节点。
 * 共享锁实现的逻辑是在获取资源成功后，如果还有剩余的共享资源，则直接通过{@link #doReleaseShared}释放当前节点，唤醒后续的节点。
 * 而排他模式是获取资源后，通过{@link #release}释放完成才会唤醒后去节点。
 * 参考{@link AQS#doAcquireShared}
 *
 * condition的实现，是在{@link ConditionObject#await}的时候将节点从队列转移到条件队列，{@link ConditionObject#signal}的时候则
 * 将条件队列中的节点添加到队列尾部。
 */
public abstract class AQS extends AbstractOwnableSynchronizer implements java.io.Serializable {

    /*---------- 构造方法 ----------*/

    protected AQS() { }

    /*---------- 支持类 ----------*/

    /**
     * 链表节点
     */
    static final class Node {
        /** 共享节点常量 */
        static final Node SHARED = new Node();
        /** 定义排他节点常量 */
        static final Node EXCLUSIVE = null;
        /** waitStatus 表示线程已经被取消. */
        static final int CANCELLED =  1;
        /** waitStatus 表示后继节点的线程需要被唤醒 （unparking） */
        static final int SIGNAL    = -1;
        /** waitStatus 表示线程处于条件等待中 */
        static final int CONDITION = -2;
        /** waitStatus 表示下一个acquireShared无条件的传播 */
        static final int PROPAGATE = -3;

        /**
         * Status field, taking on only the values:
         *   SIGNAL:    后继节点在等待当前节点唤醒，后继节点入队列时，会将前驱节点的状态更改为SIGNAL。
         *              当该节点取消或者释放锁后，必须unpark它的后继。
         *
         *   CANCELLED: 该节点由于超时或者打断被取消，进入该状态后，节点将不会再变化。
         *
         *   CONDITION: 该节点目前在一个条件等待队列中，在节点转移到主队列之前不能被
         *              用作同步的队列节点，转移到主队列后waitStatus将会被设置为0.

         *   PROPAGATE: 状态应该向后传播。这个会在doReleaseShared方法中设置（只能用于head节点），用以确保传播继续，
         *              即使其他操作介入也不会影响。

         *   0:         在条件队列转移到主队列后，会设置为0。
         *
         * 非负值表示一个节点不需要被signal。
         *
         * 该字段为正常的同步节点初始化为0
         */
        volatile int waitStatus;

        /**
         * 同步队列中的前驱节点
         */
        volatile Node prev;

        /**
         * 同步队列中的后继节点，next为null不一定就是tail节点，需要结合tail的prev确认，取消的节点next会设置为自己，而不是null。
         */
        volatile Node next;

        /**
         * 节点所属的线程
         */
        volatile Thread thread;

        /**
         * 在ConditionObject中表示下一个等待节点，在AQS同步队列中为SHARED,EXCLUSIVE，标记节点的类型。
         * 所以条件队列是单链表，同步队列是双链表。
         */
        Node nextWaiter;

        /** 是否共享模式 */
        final boolean isShared() {
            return nextWaiter == SHARED;
        }

        /** 返回前驱，或抛出空指针异常 */
        final Node predecessor() {
            Node p = prev;
            if (p == null)
                throw new NullPointerException();
            else
                return p;
        }

        /*---------- 构造函数 ----------*/
        Node() {}
        Node(Thread thread, Node nextWaiter) {
            this.nextWaiter = nextWaiter;
            this.thread = thread;
        }
        Node(Thread thread, int waitStatus) {
            this.waitStatus = waitStatus;
            this.thread = thread;
        }
    }

    /**
     * AQS的条件队列，实现Condition接口。定义了条件的wait和signal动作。
     */
    public class ConditionObject implements Condition, java.io.Serializable {
        /** 条件队列中的第一个节点 */
        private transient Node firstWaiter;
        /** 条件队列中的最后一个节点 */
        private transient Node lastWaiter;

        public ConditionObject() { }

        /*---------- 私有方法 ----------*/

        /**
         * 将当前线程加入到条件队列中。
         */
        private Node addConditionWaiter() {
            Node t = lastWaiter;
            if (t != null && t.waitStatus != Node.CONDITION) { //有非condition状态的节点，清除一次节点。
                unlinkCancelledWaiters();
                t = lastWaiter;
            }
            Node node = new Node(Thread.currentThread(), Node.CONDITION);
            if (t == null)
                firstWaiter = node;
            else
                t.nextWaiter = node;
            lastWaiter = node;
            return node;
        }

        /**
         * 将条件队列中的first删除，并调用{@link AQS#transferForSignal}方法转移到同步队列中
         */
        private void doSignal(Node first) {
            do {
                if ( (firstWaiter = first.nextWaiter) == null) //删除头结点。
                    lastWaiter = null;
                first.nextWaiter = null; //清除指针
            } while (!transferForSignal(first) && (first = firstWaiter) != null);
        }

        /**
         * 删除并调用{@link AQS#transferForSignal}方法转移所有的条件队列中的节点,
         */
        private void doSignalAll(Node first) {
            lastWaiter = firstWaiter = null;
            do {
                Node next = first.nextWaiter;
                first.nextWaiter = null;
                transferForSignal(first);
                first = next;
            } while (first != null);
        }

        /**
         * Condition队列清除非Condition状态的节点。只在获取锁时调用。
         */
        private void unlinkCancelledWaiters() {
            Node t = firstWaiter;
            Node trail = null; //记录最后一个是CONDITION状态的节点。
            while (t != null) {
                Node next = t.nextWaiter;
                if (t.waitStatus != Node.CONDITION) { //等待队列中的节点状态不是CONDITION，则需要删除
                    t.nextWaiter = null; //取消next指针

                    //过滤当前节点，连接next节点。
                    if (trail == null)
                        firstWaiter = next;
                    else
                        trail.nextWaiter = next;

                    if (next == null) //next为null 表示最后一次循环。设置lastWaiter为最后一个Condition节点。
                        lastWaiter = trail;
                }
                else
                    trail = t;
                t = next;
            }
        }


        /*---------- 公共方法 ----------*/

        /**
         * 将条件队列中等待最久的线程转移到同步队列
         */
        public final void signal() {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            Node first = firstWaiter;
            if (first != null)
                doSignal(first);
        }

        /**
         * 将条件队列中所有线程转移到同步队列
         */
        public final void signalAll() {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            Node first = firstWaiter;
            if (first != null)
                doSignalAll(first);
        }

        /** 在等待状态退出的时候，切换为中断状态 */
        private static final int REINTERRUPT =  1;
        /** 在等待状态退出的时候，抛出InterruptedException */
        private static final int THROW_IE    = -1;

        /**
         * 检查打断，未被打断过返回0，有打断，如果是transferAfterCancelledWait方法转移成功，返回THROW_IE，
         * transferAfterCancelledWait转移失败返回REINTERRUPT
         */
        private int checkInterruptWhileWaiting(Node node) {
            return Thread.interrupted() ? (transferAfterCancelledWait(node) ? THROW_IE : REINTERRUPT) : 0;
        }

        /**
         * 判断打断模式
         */
        private void reportInterruptAfterWait(int interruptMode)
            throws InterruptedException {
            if (interruptMode == THROW_IE)
                throw new InterruptedException();
            else if (interruptMode == REINTERRUPT)
                selfInterrupt();
        }

        /**
         * 可打断的条件等待，定义了条件的等待动作，释放AQS中的节点，并在条件队列中新加节点。
         */
        public final void await() throws InterruptedException {
            if (Thread.interrupted())
                throw new InterruptedException();
            Node node = addConditionWaiter(); //在条件等待队列中添加新节点。
            int savedState = fullyRelease(node); //该线程在等待的时候，释放占用资源。
            int interruptMode = 0;
            while (!isOnSyncQueue(node)) { //不在同步队列中，进入阻塞。调用signal()后，节点会从等待队列转移到同步队列，然后退出循环
                LockSupport.park(this);
                if ((interruptMode = checkInterruptWhileWaiting(node)) != 0) //有打断情况，退出循环
                    break;
            }
            if (acquireQueued(node, savedState) && interruptMode != THROW_IE) //节点转移到同步队列后，自旋调用acquire获取资源。
                interruptMode = REINTERRUPT;
            if (node.nextWaiter != null) // clean up if cancelled
                unlinkCancelledWaiters();
            if (interruptMode != 0)
                reportInterruptAfterWait(interruptMode);
        }

        /**
         * 实现不打断（不会抛出打断异常）的条件等待。
         */
        public final void awaitUninterruptibly() {
            Node node = addConditionWaiter();
            int savedState = fullyRelease(node); //释放当前节点的资源
            boolean interrupted = false;
            while (!isOnSyncQueue(node)) { //不在同步队列中，进入阻塞。
                LockSupport.park(this);
                if (Thread.interrupted())
                    interrupted = true;
            }
            //进入同步队列后
            if (acquireQueued(node, savedState) || interrupted) //尝试获取资源成功 || 过程中有打断
                selfInterrupt(); //继续打断，等待被signal
        }

        /**
         * Implements timed condition wait.
         */
        public final long awaitNanos(long nanosTimeout)
                throws InterruptedException {
            if (Thread.interrupted())
                throw new InterruptedException();
            Node node = addConditionWaiter();
            int savedState = fullyRelease(node);
            final long deadline = System.nanoTime() + nanosTimeout;
            int interruptMode = 0;
            while (!isOnSyncQueue(node)) {
                if (nanosTimeout <= 0L) {
                    transferAfterCancelledWait(node);
                    break;
                }
                if (nanosTimeout >= spinForTimeoutThreshold)
                    LockSupport.parkNanos(this, nanosTimeout);
                if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
                    break;
                nanosTimeout = deadline - System.nanoTime();
            }
            if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
                interruptMode = REINTERRUPT;
            if (node.nextWaiter != null)
                unlinkCancelledWaiters();
            if (interruptMode != 0)
                reportInterruptAfterWait(interruptMode);
            return deadline - System.nanoTime();
        }

        /**
         * Implements absolute timed condition wait.
         */
        public final boolean awaitUntil(Date deadline)
                throws InterruptedException {
            long abstime = deadline.getTime();
            if (Thread.interrupted())
                throw new InterruptedException();
            Node node = addConditionWaiter();
            int savedState = fullyRelease(node);
            boolean timedout = false;
            int interruptMode = 0;
            while (!isOnSyncQueue(node)) {
                if (System.currentTimeMillis() > abstime) {
                    timedout = transferAfterCancelledWait(node);
                    break;
                }
                LockSupport.parkUntil(this, abstime);
                if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
                    break;
            }
            if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
                interruptMode = REINTERRUPT;
            if (node.nextWaiter != null)
                unlinkCancelledWaiters();
            if (interruptMode != 0)
                reportInterruptAfterWait(interruptMode);
            return !timedout;
        }

        /**
         * Implements timed condition wait.
         */
        public final boolean await(long time, TimeUnit unit)
                throws InterruptedException {
            long nanosTimeout = unit.toNanos(time);
            if (Thread.interrupted())
                throw new InterruptedException();
            Node node = addConditionWaiter();
            int savedState = fullyRelease(node);
            final long deadline = System.nanoTime() + nanosTimeout;
            boolean timedout = false;
            int interruptMode = 0;
            while (!isOnSyncQueue(node)) {
                if (nanosTimeout <= 0L) {
                    timedout = transferAfterCancelledWait(node);
                    break;
                }
                if (nanosTimeout >= spinForTimeoutThreshold)
                    LockSupport.parkNanos(this, nanosTimeout);
                if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
                    break;
                nanosTimeout = deadline - System.nanoTime();
            }
            if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
                interruptMode = REINTERRUPT;
            if (node.nextWaiter != null)
                unlinkCancelledWaiters();
            if (interruptMode != 0)
                reportInterruptAfterWait(interruptMode);
            return !timedout;
        }


        /*---------- 条件队列支持方法 ----------*/

        /**
         * 如果condition对象是被本AQS创建，返回true。
         */
        final boolean isOwnedBy(AQS sync) {
            return sync == AQS.this;
        }

        /**
         * 查询是否还有线程在条件队列
         */
        protected final boolean hasWaiters() {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            for (Node w = firstWaiter; w != null; w = w.nextWaiter) {
                if (w.waitStatus == Node.CONDITION)
                    return true;
            }
            return false;
        }

        /**
         * 返回等待队列的长度
         */
        protected final int getWaitQueueLength() {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            int n = 0;
            for (Node w = firstWaiter; w != null; w = w.nextWaiter) {
                if (w.waitStatus == Node.CONDITION)
                    ++n;
            }
            return n;
        }

        /**
         * 返回条件队列中还在等待的线程列表。
         */
        protected final Collection<Thread> getWaitingThreads() {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            ArrayList<Thread> list = new ArrayList<Thread>();
            for (Node w = firstWaiter; w != null; w = w.nextWaiter) {
                if (w.waitStatus == Node.CONDITION) {
                    Thread t = w.thread;
                    if (t != null)
                        list.add(t);
                }
            }
            return list;
        }
    }


    /*---------- 队列方法 ----------*/

    /**
     * 自旋向队列尾部插入节点，返回节点的前驱
     */
    private Node enq(final Node node) {
        for (;;) {
            Node t = tail;
            if (t == null) { // 空队列初始化
                if (compareAndSetHead(new Node()))
                    tail = head;
            } else {
                node.prev = t; //node的前驱设置为旧的tail
                if (compareAndSetTail(t, node)) { //将tail设置为node，
                    t.next = node; //旧的tail后驱设置为node
                    return t;
                }
            }
        }
    }

    /**
     * 为当前线程创建节点并放入队列尾部，并定义EXCLUSIVE和SHARED模式
     */
    private Node addWaiter(Node mode) {
        Node node = new Node(Thread.currentThread(), mode);
        // Try the fast path of enq; backup to full enq on failure
        Node pred = tail;
        if (pred != null) { //先尝试直接enq，失败再使用自旋。
            node.prev = pred;
            if (compareAndSetTail(pred, node)) {
                pred.next = node;
                return node;
            }
        }
        enq(node);
        return node;
    }

    /**
     * 是否排他模式。在子类实现。
     * @return
     */
    protected boolean isHeldExclusively() {
        throw new UnsupportedOperationException();
    }


    private void setHead(Node node) {
        head = node;
        node.thread = null;
        node.prev = null;
    }

    /**
     * 如果当前节点的状态为负，将节点的waitStatus状态改为0，并唤醒节点的后继。
     */
    private void unparkSuccessor(Node node) {
        int ws = node.waitStatus;
        if (ws < 0) compareAndSetWaitStatus(node, ws, 0); //节点状态负数，清空状态。 失败或者被更改也没关系。

        Node s = node.next;
        if (s == null || s.waitStatus > 0) {
            //如果s为null或者CANCELED，查找node后第一个不是CANCELED的节点，其次如果tail不为null，得到tail。
            s = null;
            for (Node t = tail; t != null && t != node; t = t.prev)
                if (t.waitStatus <= 0)
                    s = t;
        }
        if (s != null) //找到s,唤醒。
            LockSupport.unpark(s.thread);
    }

    /**
     * 该方法执行唤醒后续节点并设置PROPAGATE，类似于排他模式的unparkSuccessor。
     * 注意（对于排他模式，释放锁相当于调用head节点的unparkSuccessor方法）
     */
    private void doReleaseShared() {
        for (;;) {
            Node h = head;
            if (h != null && h != tail) { //队列至少有除了tail之外的其他元素。
                int ws = h.waitStatus;
                if (ws == Node.SIGNAL) {  //如果head节点状态位SIGNAL，说明后继需要被唤醒。
                    if (!compareAndSetWaitStatus(h, Node.SIGNAL, 0)) //唤醒后继，应该将状态标记为0
                        continue;
                    unparkSuccessor(h);
                }
                else if (ws == 0 && !compareAndSetWaitStatus(h, 0, Node.PROPAGATE))
                    continue;                // ws=0，表示后继已经唤醒成功，将状态标记为PROPAGATE，失败继续循环。
            }
            if (h == head)                   // head有变化，那CAS修改状态可能失败，所以循环检查。
                break;
        }
    }

    /**
     * 共享模式的方法，设置头节点，
     * 1，调用setHead方法，
     * 2，因为是共享模式，所以在如果还有剩余资源，唤醒之后的线程。
     */
    private void setHeadAndPropagate(Node node, int propagate) {
        Node h = head; // 记录旧的head节点
        setHead(node);
        //(propagate>0)表示剩余资源大于0，可以继续唤醒之后的线程。
        // (h == null || h.waitStatus < 0) 表示原head为null或者需要传递通知。
        //((h = head) == null || h.waitStatus < 0) 表示新的head为null或者需要传递通知。
        if (propagate > 0 || h == null || h.waitStatus < 0 || (h = head) == null || h.waitStatus < 0) {
            Node s = node.next;
            if (s == null || s.isShared()) //后续节点为共享节点，则立即释放head节点。
                doReleaseShared();
        }
    }

    /*---------- Queue 检查方法，从源码中复制 ----------*/

    public final boolean hasQueuedThreads() {
        return head != tail;
    }

    public final boolean hasContended() {
        return head != null;
    }

    public final Thread getFirstQueuedThread() {
        // handle only fast path, else relay
        return (head == tail) ? null : fullGetFirstQueuedThread();
    }

    /**
     * Version of getFirstQueuedThread called when fastpath fails
     */
    private Thread fullGetFirstQueuedThread() {
        /*
         * The first node is normally head.next. Try to get its
         * thread field, ensuring consistent reads: If thread
         * field is nulled out or s.prev is no longer head, then
         * some other thread(s) concurrently performed setHead in
         * between some of our reads. We try this twice before
         * resorting to traversal.
         */
        Node h, s;
        Thread st;
        if (((h = head) != null && (s = h.next) != null &&
             s.prev == head && (st = s.thread) != null) ||
            ((h = head) != null && (s = h.next) != null &&
             s.prev == head && (st = s.thread) != null))
            return st;

        /*
         * Head's next field might not have been set yet, or may have
         * been unset after setHead. So we must check to see if tail
         * is actually first node. If not, we continue on, safely
         * traversing from tail back to head to find first,
         * guaranteeing termination.
         */

        Node t = tail;
        Thread firstThread = null;
        while (t != null && t != head) {
            Thread tt = t.thread;
            if (tt != null)
                firstThread = tt;
            t = t.prev;
        }
        return firstThread;
    }

    /**
     * 当前线程是否在队列中
     */
    public final boolean isQueued(Thread thread) {
        if (thread == null)
            throw new NullPointerException();
        for (Node p = tail; p != null; p = p.prev)
            if (p.thread == thread)
                return true;
        return false;
    }

    /**
     * 第一个线程是否为独占方式 只在 ReentrantReadWriteLock中使用.
     */
    public final boolean apparentlyFirstQueuedIsExclusive() {
        Node h, s;
        return (h = head) != null &&
            (s = h.next)  != null &&
            !s.isShared()         &&
            s.thread != null;
    }

    /**
     * 查询是否有线程比当前线程等待获取锁的时间长。
     */
    public final boolean hasQueuedPredecessors() {
        Node t = tail;
        Node h = head;
        Node s;
        return h != t && ((s = h.next) == null || s.thread != Thread.currentThread());
    }

    /*---------- 仪表及检测方法 ----------*/
    public final int getQueueLength() {
        int n = 0;
        for (Node p = tail; p != null; p = p.prev) {
            if (p.thread != null)
                ++n;
        }
        return n;
    }

    public final Collection<Thread> getQueuedThreads() {
        ArrayList<Thread> list = new ArrayList<Thread>();
        for (Node p = tail; p != null; p = p.prev) {
            Thread t = p.thread;
            if (t != null)
                list.add(t);
        }
        return list;
    }

    public final Collection<Thread> getExclusiveQueuedThreads() {
        ArrayList<Thread> list = new ArrayList<Thread>();
        for (Node p = tail; p != null; p = p.prev) {
            if (!p.isShared()) {
                Thread t = p.thread;
                if (t != null)
                    list.add(t);
            }
        }
        return list;
    }

    public final Collection<Thread> getSharedQueuedThreads() {
        ArrayList<Thread> list = new ArrayList<Thread>();
        for (Node p = tail; p != null; p = p.prev) {
            if (p.isShared()) {
                Thread t = p.thread;
                if (t != null)
                    list.add(t);
            }
        }
        return list;
    }

    public String toString() {
        int s = getState();
        String q  = hasQueuedThreads() ? "non" : "";
        return super.toString() +
            "[State = " + s + ", " + q + "empty queue]";
    }

    /*---------- Condition 支持方法 ----------*/

    /**
     * 将一个节点从条件队列转移到同步队列中。
     */
    final boolean transferForSignal(Node node) {
        //尝试取消node的条件等待状态，如果失败，返回false
        if (!compareAndSetWaitStatus(node, Node.CONDITION, 0))
            return false;

        Node p = enq(node); //向队列末尾插入node节点，并返回前驱节点。
        int ws = p.waitStatus;
        if (ws > 0 || !compareAndSetWaitStatus(p, ws, Node.SIGNAL)) //前驱节点取消 || 尝试设置为SIGNAL失败。
            LockSupport.unpark(node.thread); //唤醒node节点。
        return true;
    }

    /**
     * 返回某个一开始在田间队列中的节点现在是否在等待队列中。
     */
    final boolean isOnSyncQueue(Node node) {
        if (node.waitStatus == Node.CONDITION || node.prev == null)
            return false;
        if (node.next != null) // If has successor, it must be on queue
            return true;
        return findNodeFromTail(node);
    }

    /**
     * 从尾部向前查找节点是否存在
     */
    private boolean findNodeFromTail(Node node) {
        Node t = tail;
        for (;;) {
            if (t == node)
                return true;
            if (t == null)
                return false;
            t = t.prev;
        }
    }

    /**
     * 在取消等待后，转移节点到同步队列。
     * 将节点从CONDITION转换到同步队列中的方法除了这个方法还有transferForSignal
     *
     * @return 如果节点是由该方法转移到同步队列，则返回true。
     */
    final boolean transferAfterCancelledWait(Node node) {
        //尝试取消node的条件等待状态，如果失败，节点已经调用transferForSignal，且已经被修改
        if (compareAndSetWaitStatus(node, Node.CONDITION, 0)) {
            enq(node);
            return true;
        }

        /* 如果transferForSignal调用了，我们就只有等待，直到signal调用enq()完成。*/
        while (!isOnSyncQueue(node))
            Thread.yield();
        return false;
    }

    /**
     * 完全释放占有的资源，如果失败，将节点设置为取消状态。
     * @return 释放的资源数。
     */
    final int fullyRelease(Node node) {
        boolean failed = true;
        try {
            int savedState = getState();
            if (release(savedState)) {//使用当前state值（拥有的所有资源）调用release方法，失败则抛出异常。
                failed = false;
                return savedState;
            } else {
                throw new IllegalMonitorStateException();
            }
        } finally {
            if (failed) //释放资源失败：取消节点。
                node.waitStatus = Node.CANCELLED;
        }
    }


    /*---------- conditions 的仪表查看方法。 从源码复制 ----------*/

    /**
     * Queries whether the given ConditionObject
     */
    public final boolean owns(ConditionObject condition) {
        return condition.isOwnedBy(this);
    }

    public final boolean hasWaiters(ConditionObject condition) {
        if (!owns(condition))
            throw new IllegalArgumentException("Not owner");
        return condition.hasWaiters();
    }

    public final int getWaitQueueLength(ConditionObject condition) {
        if (!owns(condition))
            throw new IllegalArgumentException("Not owner");
        return condition.getWaitQueueLength();
    }

    public final Collection<Thread> getWaitingThreads(ConditionObject condition) {
        if (!owns(condition))
            throw new IllegalArgumentException("Not owner");
        return condition.getWaitingThreads();
    }

    /*---------- 各版本的acquire 方法 ----------*/

    /**
     * 取消一个尝试acquire的动作。设置节点状态为Node.CANCELLED
     */
    private void cancelAcquire(Node node) {
        // Ignore if node doesn't exist
        if (node == null)
            return;

        node.thread = null; //取消线程

        Node pred = node.prev;
        while (pred.waitStatus > 0)  //prev的waitStatus=CANCELED ，则跳过prev
            node.prev = pred = pred.prev;

        Node predNext = pred.next; //获取前驱的后驱，有可能不是node
        node.waitStatus = Node.CANCELLED;  //当前节点设置为取消。

        // 如果node是tail，CAS操作设置tail为pred，即删除node。
        if (node == tail && compareAndSetTail(node, pred)) {
            compareAndSetNext(pred, predNext, null);
        } else {
            int ws;
            //前驱不是头结点 && 前驱的waitStatus为SIGNAL或者可以设置为SIGNAL && 前驱的线程不为null。
            if (pred != head &&
                ((ws = pred.waitStatus) == Node.SIGNAL || (ws <= 0 && compareAndSetWaitStatus(pred, ws, Node.SIGNAL)))
                    && pred.thread != null) {
                Node next = node.next;
                if (next != null && next.waitStatus <= 0) //后驱节点存在且未取消。
                    compareAndSetNext(pred, predNext, next); //前驱跳过当前节点，连接当前节点的后驱，相当于删除prev的next指针。
            } else {//前驱是头结点，或者前驱节点thread节点为null,或者前驱节点不可SIGNAL
                unparkSuccessor(node); //unpark当前节点的后驱。（因为当前节点已经被取消）
            }

            node.next = node; // help GC
        }
    }

    /**
     * 检查和更新acquire失败节点的状态。前驱不是SIGNAL，表示不能暂停休息。
     * @return 前驱为SIGNAL，返回true，表示node可以park。
     */
    private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
        int ws = pred.waitStatus;
        if (ws == Node.SIGNAL)
            /*
             * 前驱已经为SIGNAL，说明前驱节点获取锁后会通知自己，可以安全地park。
             */
            return true;
        if (ws > 0) {
            /*
             * 前驱被取消，向前更新前驱为未取消的节点。
             */
            do {
                node.prev = pred = pred.prev;
            } while (pred.waitStatus > 0);
            pred.next = node;
        } else {
            /*
             * 0或者PROPAGATE，表示前驱正常。尝试将它设置为SIGNAL，标记为可靠的通知者。
             */
            compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
        }
        return false;
    }

    /**
     * 打断当前线程
     */
    static void selfInterrupt() {
        Thread.currentThread().interrupt();
    }

    /**
     * park并检查打断，清除打断状态。
     */
    private final boolean parkAndCheckInterrupt() {
        LockSupport.park(this);
        return Thread.interrupted();
    }

    /**
     * 启动队列中存在的某个节点的自旋，尝试成为头节点，。
     * 被{@link #acquire(int)}方法和{@link ConditionObject#await()}系列方法调用。
     * @return 返回失败状态。注意这里不是成功状态。
     */
    final boolean acquireQueued(final Node node, int arg) {
        boolean failed = true;
        try {
            boolean interrupted = false; //打断标记，表示自旋过程中节点线程是否被打断过。
            //自旋：不断判断node前驱是否为头节点，并获取锁。否则尝试park。
            for (;;) {
                final Node p = node.predecessor(); //node的前驱。
                if (p == head && tryAcquire(arg)) { // 如果前驱是头节点，且tryAcquire成功，说明成功获取锁。
                    // 将head设置为当前节点。删除旧的head节点。等待线程执行完后，调用release方法释放资源。和共享锁有区别
                    setHead(node);
                    p.next = null; // help GC
                    failed = false;
                    return interrupted;
                }
                // 获取失败，判断是否应该park，如果应该被park，则park并检查打断状态。
                if (shouldParkAfterFailedAcquire(p, node) && parkAndCheckInterrupt())
                    interrupted = true; //自旋过程中，如果被打断过，哪怕只有一次，设置interrupted为true。
            }
        } finally {
            if (failed)
                cancelAcquire(node); //失败，取消acquire。
        }
    }

    /**
     * 对未在同步队列中的节点执行排他型acquire，和acquireQueued类似，有打断，直接抛出打断异常。
     */
    private void doAcquireInterruptibly(int arg)
        throws InterruptedException {
        final Node node = addWaiter(Node.EXCLUSIVE); //和acquireQueued相比，多出将节点放入队列中的步骤。
        boolean failed = true;
        try {
            for (;;) {
                final Node p = node.predecessor();
                if (p == head && tryAcquire(arg)) {
                    setHead(node);
                    p.next = null; // help GC
                    failed = false;
                    return;
                }
                if (shouldParkAfterFailedAcquire(p, node) && parkAndCheckInterrupt())
                    throw new InterruptedException();
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }

    /**
     * Acquires in exclusive timed mode.
     */
    private boolean doAcquireNanos(int arg, long nanosTimeout)
            throws InterruptedException {
        if (nanosTimeout <= 0L)
            return false;
        final long deadline = System.nanoTime() + nanosTimeout;
        final Node node = addWaiter(Node.EXCLUSIVE);
        boolean failed = true;
        try {
            for (;;) {
                final Node p = node.predecessor();
                if (p == head && tryAcquire(arg)) {
                    setHead(node);
                    p.next = null; // help GC
                    failed = false;
                    return true;
                }
                nanosTimeout = deadline - System.nanoTime();
                if (nanosTimeout <= 0L)
                    return false;
                if (shouldParkAfterFailedAcquire(p, node) && nanosTimeout > spinForTimeoutThreshold)
                    LockSupport.parkNanos(this, nanosTimeout);
                if (Thread.interrupted())
                    throw new InterruptedException();
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }

    /**
     * 共享模式获取资源
     */
    private void doAcquireShared(int arg) {
        final Node node = addWaiter(Node.SHARED); //添加新的共享类型节点到队列中，共享节点的nextWaiter为Node.SHARED。
        boolean failed = true;
        try {
            boolean interrupted = false;
            for (;;) {
                final Node p = node.predecessor();
                if (p == head) {
                    int r = tryAcquireShared(arg);
                    if (r >= 0) {
                        //共享模式下，如果后续节点为SHARED，则直接释放头结点。对比独占模式，释放掉资源后才唤醒后继。
                        setHeadAndPropagate(node, r);
                        p.next = null; // help GC
                        if (interrupted)
                            selfInterrupt();
                        failed = false;
                        return;
                    }
                }
                if (shouldParkAfterFailedAcquire(p, node) && parkAndCheckInterrupt())
                    interrupted = true;
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }

    /**
     * 共享模式下acquire 打断抛出异常.
     * @param arg the acquire argument
     */
    private void doAcquireSharedInterruptibly(int arg)
        throws InterruptedException {
        final Node node = addWaiter(Node.SHARED);
        boolean failed = true;
        try {
            for (;;) {
                final Node p = node.predecessor();
                if (p == head) {
                    int r = tryAcquireShared(arg);
                    if (r >= 0) {
                        setHeadAndPropagate(node, r);
                        p.next = null; // help GC
                        failed = false;
                        return;
                    }
                }
                if (shouldParkAfterFailedAcquire(p, node) &&
                    parkAndCheckInterrupt())
                    throw new InterruptedException();
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }

    /**
     * 共享模式下acquire.
     */
    private boolean doAcquireSharedNanos(int arg, long nanosTimeout)
            throws InterruptedException {
        if (nanosTimeout <= 0L)
            return false;
        final long deadline = System.nanoTime() + nanosTimeout;
        final Node node = addWaiter(Node.SHARED);
        boolean failed = true;
        try {
            for (;;) {
                final Node p = node.predecessor();
                if (p == head) {
                    int r = tryAcquireShared(arg);
                    if (r >= 0) {
                        setHeadAndPropagate(node, r);
                        p.next = null; // help GC
                        failed = false;
                        return true;
                    }
                }
                nanosTimeout = deadline - System.nanoTime();
                if (nanosTimeout <= 0L)
                    return false;
                if (shouldParkAfterFailedAcquire(p, node) &&
                    nanosTimeout > spinForTimeoutThreshold)
                    LockSupport.parkNanos(this, nanosTimeout);
                if (Thread.interrupted())
                    throw new InterruptedException();
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }

    /*---------- 主要的外部调用方法 ----------*/

    /**
     * 尝试获取一个排他锁，在子类中实现
     * 该方法可以用来实现 Lock.tryLock()
     *
     */
    protected boolean tryAcquire(int arg) {
        throw new UnsupportedOperationException();
    }

    /**
     * 尝试释放一个排他锁，在在内中实现。
     */
    protected boolean tryRelease(int arg) {
        throw new UnsupportedOperationException();
    }

    protected int tryAcquireShared(int arg) {
        throw new UnsupportedOperationException();
    }

    public final boolean tryAcquireSharedNanos(int arg, long nanosTimeout)
            throws InterruptedException {
        if (Thread.interrupted())
            throw new InterruptedException();
        return tryAcquireShared(arg) >= 0 ||
            doAcquireSharedNanos(arg, nanosTimeout);
    }

    public final boolean tryAcquireNanos(int arg, long nanosTimeout)
            throws InterruptedException {
        if (Thread.interrupted())
            throw new InterruptedException();
        return tryAcquire(arg) || doAcquireNanos(arg, nanosTimeout);
    }

    protected boolean tryReleaseShared(int arg) {
        throw new UnsupportedOperationException();
    }

    /**
     * 释放一个排他锁。相当于唤醒后续节点。
     * 该方法可以用来实现 Lock.unlock()
     */
    public final boolean release(int arg) {
        if (tryRelease(arg)) { //释放锁成功，唤醒后续节点
            Node h = head;
            if (h != null && h.waitStatus != 0)
                unparkSuccessor(h);
            return true;
        }
        return false;
    }

    public final boolean releaseShared(int arg) {
        if (tryReleaseShared(arg)) {
            doReleaseShared();
            return true;
        }
        return false;
    }

    /**
     * lock直接调用的方法， 获取独占式资源。
     */
    public final void acquire(int arg) {
        //先尝试获取锁，如果失败，则加入队列并自旋。
        if (!tryAcquire(arg) && acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
            selfInterrupt(); //如果设置了打断状态，则调用selfInterrupt方法打断
    }

    public final void acquireInterruptibly(int arg)
            throws InterruptedException {
        if (Thread.interrupted())
            throw new InterruptedException();
        if (!tryAcquire(arg))
            doAcquireInterruptibly(arg);
    }

    public final void acquireShared(int arg) {
        if (tryAcquireShared(arg) < 0)
            doAcquireShared(arg);
    }

    public final void acquireSharedInterruptibly(int arg)
            throws InterruptedException {
        if (Thread.interrupted())
            throw new InterruptedException();
        if (tryAcquireShared(arg) < 0)
            doAcquireSharedInterruptibly(arg);
    }

    /*---------- 字段 ----------*/

    /** 自旋超时阈值 */
    static final long spinForTimeoutThreshold = 1000L;

    /** 队列头节点 */
    private transient volatile Node head;  //AQS在空队列时，tail指向head,并且都为null。
    /** 队列尾节点 */
    private transient volatile Node tail;
    /** 同步状态 */
    private volatile int state;

    protected final int getState() { return state; }

    protected final void setState(int newState) { state = newState; }

    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long stateOffset;
    private static final long headOffset;
    private static final long tailOffset;
    private static final long waitStatusOffset;
    private static final long nextOffset;

    static {
        try {
            stateOffset = unsafe.objectFieldOffset
                (AQS.class.getDeclaredField("state"));
            headOffset = unsafe.objectFieldOffset
                (AQS.class.getDeclaredField("head"));
            tailOffset = unsafe.objectFieldOffset
                (AQS.class.getDeclaredField("tail"));
            waitStatusOffset = unsafe.objectFieldOffset
                (Node.class.getDeclaredField("waitStatus"));
            nextOffset = unsafe.objectFieldOffset
                (Node.class.getDeclaredField("next"));

        } catch (Exception ex) { throw new Error(ex); }
    }

    /**
     * CAS head field. Used only by enq.
     */
    private final boolean compareAndSetHead(Node update) {
        return unsafe.compareAndSwapObject(this, headOffset, null, update);
    }

    /**
     * CAS tail field. Used only by enq.
     */
    private final boolean compareAndSetTail(Node expect, Node update) {
        return unsafe.compareAndSwapObject(this, tailOffset, expect, update);
    }

    /**
     * CAS waitStatus field of a node.
     */
    private static final boolean compareAndSetWaitStatus(Node node, int expect, int update) {
        return unsafe.compareAndSwapInt(node, waitStatusOffset, expect, update);
    }

    /**
     * CAS next field of a node.
     */
    private static final boolean compareAndSetNext(Node node, Node expect, Node update) {
        return unsafe.compareAndSwapObject(node, nextOffset, expect, update);
    }

    /**
     * CAS state field of a node.
     */
    protected final boolean compareAndSetState(int expect, int update) {
        return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
    }
}
