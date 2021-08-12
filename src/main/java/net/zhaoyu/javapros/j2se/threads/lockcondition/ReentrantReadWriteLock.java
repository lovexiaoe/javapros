package net.zhaoyu.javapros.j2se.threads.lockcondition;


import net.zhaoyu.javapros.j2se.threads.basic.AQS;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * ReentrantReadWriteLock 源码解析
 */
public class ReentrantReadWriteLock implements ReadWriteLock, java.io.Serializable {

    abstract static class Sync extends AQS {
        /*
         * 读和写线程计数用到的常量
         * state 被分为两个区域，低十六位分配给独占锁（写）用于计数。高十六位被分配被共享锁（读）用于计数。
         * 对于排他锁，低16位可以代表重入次数，对于共享锁，就复杂了，因为高16位代表的是持有共享锁的线程个数，
         * 所以使用HoldCounter记录每个线程的可重入次数。
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
         * 每个读线程拥有的可重入次数。 存放在ThreadLocal里，和线程关联。
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
         * 当前读线程拥有的重入次数。
         * 只会在构造方法和readObject中初始化， 当线程的重入次数为0，则删除，
         */
        private transient ThreadLocalHoldCounter readHolds;

        /**
         * 缓存最后一次线程成功获取的读锁记录。用以提高性能。
         * 因为通常读锁获取后会很快释放。在 读取->释放 这段时间内，没有其他读取线程获得该锁，该缓存将提高性能。
         */
        private transient HoldCounter cachedHoldCounter;

        /**
         * 第一个获取读锁的线程。
         * 在只有一个线程的情况下，就不需要用到readHolds了，而大多数情况下，读线程通常只有一个，不会存在竞争。
         * 就是只有一站路就骑单车，就用不到火车了。firstReader就是单车，readHolds就是火车，只有人多的时候才发车，提高性能。
         */
        private transient Thread firstReader = null;

        /**
         * 第一个获取读锁线程拥有的线程数。提高性能
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
                步骤：
                1.如果写锁被另一个线程占用，失败。
                2.否则，owner为当前线程，如果队列策略不阻塞当前线程，CAS操作尝试增加共享状态（注意，该步不检查阻塞情况，推迟到
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
                if (r == 0) { //如果读锁是空闲的，记录首次读取
                    firstReader = current;
                    firstReaderHoldCount = 1;
                } else if (firstReader == current) { //如果是首次读取，增加读取计数。
                    firstReaderHoldCount++;
                } else { //非首次读取获得锁，
                    HoldCounter rh = cachedHoldCounter; //获取最后一次读锁的缓存
                    if (rh == null || rh.tid != getThreadId(current)) //没有缓存，或者缓存不是当前线程的。
                        cachedHoldCounter = rh = readHolds.get(); //重新更新缓存
                    else if (rh.count == 0) //当前线程是最后一次读锁缓存的线程，可重入次数为0，说明非重入
                        readHolds.set(rh);//为了性能在重入计数为0会删除readHolds记录，需要重新设置readHolds。
                    rh.count++; //缓存计数自增。
                }
                return 1;
            }
            return fullTryAcquireShared(current);
        }

        /**
         * 和tryAcquireShared类似，tryAcquireShared是fullTryAcquireShared的fast版本，除了tryAcquireShared的功能外，
         * 还处理了CAS失败和阻塞情况。
         */
        final int fullTryAcquireShared(Thread current) {
            HoldCounter rh = null;
            for (;;) { //自旋
                int c = getState();
                if (exclusiveCount(c) != 0) {
                    if (getExclusiveOwnerThread() != current) //写锁有占用且owner线程非当前线程，失败。
                        return -1;
                } else if (readerShouldBlock()) {//写锁空闲，且读锁应该被阻塞，说明 head.next正在等待获取写锁
                    //读锁阻塞，非重入情况下，获取失败。
                    if (firstReader == current) { //当前线程是第一个读锁，可重入。
                        // 首读线程存在，说明firstReaderHoldCount>0，即重入的情况，不做任何处理。
                    } else { //当前线程非首读线程，初始化rh
                        if (rh == null) {
                            rh = cachedHoldCounter;
                            if (rh == null || rh.tid != getThreadId(current)) { //当前线程的rh未初始化，则从ThreadLocal里面拿。
                                rh = readHolds.get();
                                if (rh.count == 0) //读锁重入次数为0，则清除readHolds。
                                    readHolds.remove(); //清除线程占用资源，提高效率。
                            }
                        }
                        if (rh.count == 0) //重入次数为0，说明是非重入，获取锁失败。
                            return -1;
                    }
                }
                if (sharedCount(c) == MAX_COUNT) //计数达到最大限制
                    throw new Error("Maximum lock count exceeded");
                if (compareAndSetState(c, c + SHARED_UNIT)) { //尝试增加共享状态
                    if (sharedCount(c) == 0) { //读锁空闲，记录首读线程
                        firstReader = current;
                        firstReaderHoldCount = 1;
                    } else if (firstReader == current) {
                        firstReaderHoldCount++;
                    } else {
                        if (rh == null)
                            rh = cachedHoldCounter;
                        if (rh == null || rh.tid != getThreadId(current))
                            rh = readHolds.get();
                        else if (rh.count == 0) //可重入次数为0，说明非重入，重新设置readHolds
                            readHolds.set(rh);
                        rh.count++;
                        cachedHoldCounter = rh; // cache for release
                    }
                    return 1;
                }
            }
        }

        /**
         * 该方法和tryAcquire作用一样，不考虑阻塞的情况，不调用writerShouldBlock。
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
         * 方法和tryAcquireShared类似，不考虑阻塞的情况，不调用readerShouldBlock
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

        final ConditionObject newCondition() {
            return new ConditionObject();
        }

        final Thread getOwner() {
            return ((exclusiveCount(getState()) == 0) ? null : getExclusiveOwnerThread());
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
         * 从流中重组一个实例，（即反序列化）。
         */
        private void readObject(java.io.ObjectInputStream s)
            throws java.io.IOException, ClassNotFoundException {
            s.defaultReadObject();
            readHolds = new ThreadLocalHoldCounter();
            setState(0); // reset to unlocked state
        }

        final int getCount() { return getState(); }
    }


    /**
     * 公平锁，非head节点都要阻塞。
     */
    static final class FairSync extends Sync {
        final boolean writerShouldBlock() {
            return hasQueuedPredecessors();
        }
        final boolean readerShouldBlock() {
            return hasQueuedPredecessors();
        }
    }

    /**
     * 非公平锁，写入一直不阻塞。
     */
    static final class NonfairSync extends Sync {
        final boolean writerShouldBlock() {
            return false; //
        }
        final boolean readerShouldBlock() {
            /*  一种避免写锁无线等待的启发式做法。
                head后的节点是写节点，则阻塞，如果head后是读节点，再后才是写节点，就不起作用。
                只是一种优化，不一定有效。
             */
            return apparentlyFirstQueuedIsExclusive();
        }
    }

    public static class ReadLock implements Lock, java.io.Serializable {
        private final Sync sync;

        /**
         * ReentrantReadWriteLock参数的构造方法。
         */
        protected ReadLock(ReentrantReadWriteLock lock) {
            sync = lock.sync;
        }

        public void lock() {
            sync.acquireShared(1);
        }

        public void lockInterruptibly() throws InterruptedException {
            sync.acquireSharedInterruptibly(1);
        }

        /**
         * 不检查阻塞，尝试获取锁。
         */
        public boolean tryLock() {
            return sync.tryReadLock();
        }

        public boolean tryLock(long timeout, TimeUnit unit)
                throws InterruptedException {
            return sync.tryAcquireSharedNanos(1, unit.toNanos(timeout));
        }

        public void unlock() {
            sync.releaseShared(1);
        }

        /**
         * 写锁不支持创建Condition。
         */
        public Condition newCondition() {
            throw new UnsupportedOperationException();
        }

        public String toString() {
            int r = sync.getReadLockCount();
            return super.toString() +
                "[Read locks = " + r + "]";
        }
    }

    public static class WriteLock implements Lock, java.io.Serializable {
        private final Sync sync;

        /**
         * ReentrantReadWriteLock 参数的构造方法。
         */
        protected WriteLock(ReentrantReadWriteLock lock) {
            sync = lock.sync;
        }

        public void lock() {
            sync.acquire(1);
        }

        public void lockInterruptibly() throws InterruptedException {
            sync.acquireInterruptibly(1);
        }

        /**
         * 不检查阻塞，尝试获得锁。
         */
        public boolean tryLock( ) {
            return sync.tryWriteLock();
        }

        public boolean tryLock(long timeout, TimeUnit unit)
                throws InterruptedException {
            return sync.tryAcquireNanos(1, unit.toNanos(timeout));
        }

        public void unlock() {
            sync.release(1);
        }

        /**
         * 为该写锁实例，创建Condition。
         */
        public Condition newCondition() {
            return sync.newCondition();
        }


        public String toString() {
            Thread o = sync.getOwner();
            return super.toString() + ((o == null) ? "[Unlocked]" : "[Locked by thread " + o.getName() + "]");
        }

        public boolean isHeldByCurrentThread() {
            return sync.isHeldExclusively();
        }

        public int getHoldCount() {
            return sync.getWriteHoldCount();
        }
    }


    /*---------- 字段 ----------*/

    final Sync sync;

    private final ReentrantReadWriteLock.ReadLock readerLock;

    private final ReentrantReadWriteLock.WriteLock writerLock;


    /*---------- 构造方法 ----------*/

    //默认创建非公平锁。
    public ReentrantReadWriteLock() {
        this(false);
    }

    public ReentrantReadWriteLock(boolean fair) {
        sync = fair ? new FairSync() : new NonfairSync();
        readerLock = new ReadLock(this);
        writerLock = new WriteLock(this);
    }

    /*---------- 查看监控表盘和状态方法 ----------*/

    protected Thread getOwner() {
        return sync.getOwner();
    }

    public int getReadLockCount() {
        return sync.getReadLockCount();
    }

    public boolean isWriteLocked() {
        return sync.isWriteLocked();
    }

    public boolean isWriteLockedByCurrentThread() {
        return sync.isHeldExclusively();
    }

    public int getWriteHoldCount() {
        return sync.getWriteHoldCount();
    }

    public int getReadHoldCount() {
        return sync.getReadHoldCount();
    }

    protected Collection<Thread> getQueuedWriterThreads() {
        return sync.getExclusiveQueuedThreads();
    }

    protected Collection<Thread> getQueuedReaderThreads() {
        return sync.getSharedQueuedThreads();
    }

    public final boolean hasQueuedThreads() {
        return sync.hasQueuedThreads();
    }

    public final boolean hasQueuedThread(Thread thread) {
        return sync.isQueued(thread);
    }

    public final int getQueueLength() {
        return sync.getQueueLength();
    }

    protected Collection<Thread> getQueuedThreads() {
        return sync.getQueuedThreads();
    }

    public boolean hasWaiters(Condition condition) {
        if (condition == null)
            throw new NullPointerException();
        if (!(condition instanceof AQS.ConditionObject))
            throw new IllegalArgumentException("not owner");
        return sync.hasWaiters((AQS.ConditionObject)condition);
    }

    public int getWaitQueueLength(Condition condition) {
        if (condition == null)
            throw new NullPointerException();
        if (!(condition instanceof AQS.ConditionObject))
            throw new IllegalArgumentException("not owner");
        return sync.getWaitQueueLength((AQS.ConditionObject)condition);
    }

    protected Collection<Thread> getWaitingThreads(Condition condition) {
        if (condition == null)
            throw new NullPointerException();
        if (!(condition instanceof AQS.ConditionObject))
            throw new IllegalArgumentException("not owner");
        return sync.getWaitingThreads((AQS.ConditionObject)condition);
    }

    /**
     * 返回线程id，我们不能直接使用Thread.getId()，因为Thread.getId()不是final，且被重写
     */
    static final long getThreadId(Thread thread) {
        return UNSAFE.getLongVolatile(thread, TID_OFFSET);
    }

    /*---------- 重载方法 ----------*/

    public String toString() {
        int c = sync.getCount();
        int w = Sync.exclusiveCount(c);
        int r = Sync.sharedCount(c);

        return super.toString() +
            "[Write locks = " + w + ", Read locks = " + r + "]";
    }

    public ReentrantReadWriteLock.WriteLock writeLock() { return writerLock; }
    public ReentrantReadWriteLock.ReadLock  readLock()  { return readerLock; }


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

