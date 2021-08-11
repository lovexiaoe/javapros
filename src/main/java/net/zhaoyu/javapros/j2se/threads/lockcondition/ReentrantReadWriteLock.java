package net.zhaoyu.javapros.j2se.threads.lockcondition;


import net.zhaoyu.javapros.j2se.threads.basic.AQS;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * ReentrantReadWriteLock 源码解析
 */
public class ReentrantReadWriteLock implements Lock, java.io.Serializable {

    abstract static class Sync extends AQS {
        /*
         * 读和写线程计数用到的常量
         * state 被分为两个区域，低十六位分配给独占锁（写）用于计数。高十六位被分配被共享锁（读）用于计数。
         */

        static final int SHARED_SHIFT = 16;
        static final int SHARED_UNIT = (1 << SHARED_SHIFT);
        static final int MAX_COUNT = (1 << SHARED_SHIFT) - 1;
        static final int EXCLUSIVE_MASK = (1 << SHARED_SHIFT) - 1;


        /** 共享锁的计数。 取高16位*/
        static int sharedCount(int c)    { return c >>> SHARED_SHIFT; }
        /** 排他锁拥有的计数 ，取低16位*/
        static int exclusiveCount(int c) { return c & EXCLUSIVE_MASK; }


        /**
         * 每个线程拥有的read资源计数。 存放在ThreadLocal里.
         */
        static final class HoldCounter {
            int count = 0;
            // Use id, not reference, to avoid garbage retention
            final long tid = getThreadId(Thread.currentThread());
        }

        /**
         * 存放HoldCounter的ThreadLocal
         */
        static final class ThreadLocalHoldCounter extends ThreadLocal<HoldCounter> {
            public HoldCounter initialValue() {
                return new HoldCounter();
            }
        }


        /**
         * 当前线程拥有的可重入读锁的数量。
         * 只会在构造方法和readObject中初始化， 当线程的读锁数为0，则删除。
         */
        private transient ThreadLocalHoldCounter readHolds;

        /**
         * 最后一个线程可以成功获取的读锁数量。
         */
        private transient HoldCounter cachedHoldCounter;

        /**
         * 第一个获取读锁的线程。
         */
        private transient Thread firstReader = null;

        /**
         * 第一个获取读锁线程拥有的线程数。
         */
        private transient int firstReaderHoldCount;

        Sync() {
            readHolds = new ThreadLocalHoldCounter();
            setState(getState()); // 确保 readHolds 的内存可见性，这里不明白为何这样写。
        }

        abstract boolean readerShouldBlock();

        abstract boolean writerShouldBlock();


        /**
         * 释放写锁。
         */
        protected final boolean tryRelease(int releases) {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
            int nextc = getState() - releases;
            boolean free = exclusiveCount(nextc) == 0; //写锁计数是否完全释放。
            if (free)
                setExclusiveOwnerThread(null);
            setState(nextc);
            return free;
        }

        /**
         * 获取写锁
         */
        protected final boolean tryAcquire(int acquires) {
            /*
                步骤：
                1.如果读锁计数非0或者写锁计数非0，且owner不是当前线程，失败。
                2.如果计数已经饱和，失败。
                3.其他情况，如果可以获取计数，则设置状态并设置owner。
             */
            Thread current = Thread.currentThread();
            int c = getState();
            int w = exclusiveCount(c);
            if (c != 0) { //资源状态不为0。
                // 写锁计数w==0说明读锁计数不为0。
                if (w == 0 || current != getExclusiveOwnerThread()) //存在写锁 || 当前线程不占有锁。
                    return false;
                if (w + exclusiveCount(acquires) > MAX_COUNT) //获取资源超过最大值.
                    throw new Error("Maximum lock count exceeded");
                // 执行到此，说明是重入锁。
                setState(c + acquires); //设置state，占用计数。
                return true;
            }
            //下面为状态资源为0的情况。
            if (writerShouldBlock() || !compareAndSetState(c, c + acquires)) //尝试设置state占用资源，
                return false;
            setExclusiveOwnerThread(current); //设置占用线程为当前线程。
            return true;
        }

        /**
         * 释放共享锁
         */
        protected final boolean tryReleaseShared(int unused) {
            Thread current = Thread.currentThread();
            if (firstReader == current) { //是首读线程。
                // assert firstReaderHoldCount > 0;
                if (firstReaderHoldCount == 1)
                    firstReader = null;
                else
                    firstReaderHoldCount--; //首读线程计数减1
            } else {
                HoldCounter rh = cachedHoldCounter;
                if (rh == null || rh.tid != getThreadId(current))
                    rh = readHolds.get();
                int count = rh.count;
                if (count <= 1) {
                    readHolds.remove();
                    if (count <= 0)
                        throw unmatchedUnlockException();
                }
                --rh.count;
            }
            for (;;) {
                int c = getState(); //获取全部锁状态。
                int nextc = c - SHARED_UNIT; //高16位减1
                if (compareAndSetState(c, nextc))
                    return nextc == 0; //返回读锁是否释放完。
            }
        }

        private IllegalMonitorStateException unmatchedUnlockException() {
            return new IllegalMonitorStateException(
                "attempt to unlock read lock, not locked by current thread");
        }

        /**
         * 获取共享锁
         */
        protected final int tryAcquireShared(int unused) {
            /*
             * Walkthrough:
             * 1. If write lock held by another thread, fail.
             * 2. Otherwise, this thread is eligible for
             *    lock wrt state, so ask if it should block
             *    because of queue policy. If not, try
             *    to grant by CASing state and updating count.
             *    Note that step does not check for reentrant
             *    acquires, which is postponed to full version
             *    to avoid having to check hold count in
             *    the more typical non-reentrant case.
             * 3. If step 2 fails either because thread
             *    apparently not eligible or CAS fails or count
             *    saturated, chain to version with full retry loop.
             */
            /*
                步骤：
                1.如果写锁被另一个线程占用，失败。
                2.否则，owner为当前线程，如果队列策略不阻塞当前线程，CAS操作尝试增加共享状态（注意，该步不检查重入锁，推迟到
                    full版本检查，避免产生过多非重入情况的检查）。
                3.如果第二部失败，调用full版本，进行循环检查。
             */
            Thread current = Thread.currentThread();
            int c = getState();
            if (exclusiveCount(c) != 0 && getExclusiveOwnerThread() != current) //写锁有占用且owner线程非当前线程，失败。
                return -1;
            int r = sharedCount(c); //得到共享资源计数
            if (!readerShouldBlock() && r < MAX_COUNT
                    && compareAndSetState(c, c + SHARED_UNIT)) {//尝试增加共享状态
                if (r == 0) { //记录首次读取
                    firstReader = current;
                    firstReaderHoldCount = 1;
                } else if (firstReader == current) { //如果是首次读取，增加读取计数。
                    firstReaderHoldCount++;
                } else { //非首次读取，
                    HoldCounter rh = cachedHoldCounter;
                    if (rh == null || rh.tid != getThreadId(current))
                        cachedHoldCounter = rh = readHolds.get();
                    else if (rh.count == 0)
                        readHolds.set(rh);
                    rh.count++;
                }
                return 1;
            }
            return fullTryAcquireShared(current);
        }

        /**
         * Full version of acquire for reads, that handles CAS misses
         * and reentrant reads not dealt with in tryAcquireShared.
         */
        final int fullTryAcquireShared(Thread current) {
            /*
             * This code is in part redundant with that in
             * tryAcquireShared but is simpler overall by not
             * complicating tryAcquireShared with interactions between
             * retries and lazily reading hold counts.
             */
            HoldCounter rh = null;
            for (;;) {
                int c = getState();
                if (exclusiveCount(c) != 0) {
                    if (getExclusiveOwnerThread() != current)
                        return -1;
                    // else we hold the exclusive lock; blocking here
                    // would cause deadlock.
                } else if (readerShouldBlock()) {
                    // Make sure we're not acquiring read lock reentrantly
                    if (firstReader == current) {
                        // assert firstReaderHoldCount > 0;
                    } else {
                        if (rh == null) {
                            rh = cachedHoldCounter;
                            if (rh == null || rh.tid != getThreadId(current)) {
                                rh = readHolds.get();
                                if (rh.count == 0)
                                    readHolds.remove();
                            }
                        }
                        if (rh.count == 0)
                            return -1;
                    }
                }
                if (sharedCount(c) == MAX_COUNT)
                    throw new Error("Maximum lock count exceeded");
                if (compareAndSetState(c, c + SHARED_UNIT)) {
                    if (sharedCount(c) == 0) {
                        firstReader = current;
                        firstReaderHoldCount = 1;
                    } else if (firstReader == current) {
                        firstReaderHoldCount++;
                    } else {
                        if (rh == null)
                            rh = cachedHoldCounter;
                        if (rh == null || rh.tid != getThreadId(current))
                            rh = readHolds.get();
                        else if (rh.count == 0)
                            readHolds.set(rh);
                        rh.count++;
                        cachedHoldCounter = rh; // cache for release
                    }
                    return 1;
                }
            }
        }

        /**
         * Performs tryLock for write, enabling barging in both modes.
         * This is identical in effect to tryAcquire except for lack
         * of calls to writerShouldBlock.
         */
        final boolean tryWriteLock() {
            Thread current = Thread.currentThread();
            int c = getState();
            if (c != 0) {
                int w = exclusiveCount(c);
                if (w == 0 || current != getExclusiveOwnerThread())
                    return false;
                if (w == MAX_COUNT)
                    throw new Error("Maximum lock count exceeded");
            }
            if (!compareAndSetState(c, c + 1))
                return false;
            setExclusiveOwnerThread(current);
            return true;
        }

        /**
         * Performs tryLock for read, enabling barging in both modes.
         * This is identical in effect to tryAcquireShared except for
         * lack of calls to readerShouldBlock.
         */
        final boolean tryReadLock() {
            Thread current = Thread.currentThread();
            for (;;) {
                int c = getState();
                if (exclusiveCount(c) != 0 &&
                    getExclusiveOwnerThread() != current)
                    return false;
                int r = sharedCount(c);
                if (r == MAX_COUNT)
                    throw new Error("Maximum lock count exceeded");
                if (compareAndSetState(c, c + SHARED_UNIT)) {
                    if (r == 0) {
                        firstReader = current;
                        firstReaderHoldCount = 1;
                    } else if (firstReader == current) {
                        firstReaderHoldCount++;
                    } else {
                        HoldCounter rh = cachedHoldCounter;
                        if (rh == null || rh.tid != getThreadId(current))
                            cachedHoldCounter = rh = readHolds.get();
                        else if (rh.count == 0)
                            readHolds.set(rh);
                        rh.count++;
                    }
                    return true;
                }
            }
        }

        //当前线程是否是锁拥有者。
        protected final boolean isHeldExclusively() {
            return getExclusiveOwnerThread() == Thread.currentThread();
        }

        // Methods relayed to outer class

        final ConditionObject newCondition() {
            return new ConditionObject();
        }

        final Thread getOwner() {
            // Must read state before owner to ensure memory consistency
            return ((exclusiveCount(getState()) == 0) ?
                    null :
                    getExclusiveOwnerThread());
        }

        final int getReadLockCount() {
            return sharedCount(getState());
        }

        final boolean isWriteLocked() {
            return exclusiveCount(getState()) != 0;
        }

        final int getWriteHoldCount() {
            return isHeldExclusively() ? exclusiveCount(getState()) : 0;
        }

        final int getReadHoldCount() {
            if (getReadLockCount() == 0)
                return 0;

            Thread current = Thread.currentThread();
            if (firstReader == current)
                return firstReaderHoldCount;

            HoldCounter rh = cachedHoldCounter;
            if (rh != null && rh.tid == getThreadId(current))
                return rh.count;

            int count = readHolds.get().count;
            if (count == 0) readHolds.remove();
            return count;
        }

        /**
         * Reconstitutes the instance from a stream (that is, deserializes it).
         */
        private void readObject(java.io.ObjectInputStream s)
            throws java.io.IOException, ClassNotFoundException {
            s.defaultReadObject();
            readHolds = new ThreadLocalHoldCounter();
            setState(0); // reset to unlocked state
        }

        final int getCount() { return getState(); }
    }

    @Override
    public void lock() {

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public Condition newCondition() {
        return null;
    }


    /**
     * 返回线程id，我们不能直接使用Thread.getId()，因为Thread.getId()不是final，且被重写
     */
    static final long getThreadId(Thread thread) {
        return UNSAFE.getLongVolatile(thread, TID_OFFSET);
    }

    // Unsafe mechanics
    private static final sun.misc.Unsafe UNSAFE;
    private static final long TID_OFFSET;
    static {
        try {
            UNSAFE = sun.misc.Unsafe.getUnsafe();
            Class<?> tk = Thread.class;
            TID_OFFSET = UNSAFE.objectFieldOffset(tk.getDeclaredField("tid"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }
}

