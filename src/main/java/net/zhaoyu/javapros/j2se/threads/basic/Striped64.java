package net.zhaoyu.javapros.j2se.threads.basic;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.DoubleBinaryOperator;
import java.util.function.LongBinaryOperator;

/**
 * JUC包中提供了Stripe64类。基于分段思想，在竞争激烈的时候尽量分散竞争。支持对64bit的值进行动态分段的机制和表示。
 * 分解表示：对于一个数字 5，可以分解为一序列数的和：2 + 3，这个数字加 1 也等价于它的分解序列中的任一数字加1：5 + 1 = 2 + (3 + 1)。
 *
 * Stripe64有使用base,和Cell数组存放值，在没有竞争的情况下，使用base字段，发生竞争时，当base的cas操作失败，才创建cell并 使用Cell数组。
 * cell数组大小初始化为2，翻倍增长，直到大于等于CPU数量。
 *
 * 所以Stripe64表示的值是base加cell的总和。
 */
abstract class Striped64 extends Number{

    @sun.misc.Contended static final class Cell {
        volatile long value;
        Cell(long x) { value = x; }
        final boolean cas(long cmp, long val) {
            return UNSAFE.compareAndSwapLong(this, valueOffset, cmp, val);
        }

        // Unsafe mechanics
        private static final sun.misc.Unsafe UNSAFE;
        private static final long valueOffset;
        static {
            try {
                UNSAFE = sun.misc.Unsafe.getUnsafe();
                Class<?> ak = Cell.class;
                valueOffset = UNSAFE.objectFieldOffset
                        (ak.getDeclaredField("value"));
            } catch (Exception e) {
                throw new Error(e);
            }
        }
    }

    static final int NCPU = Runtime.getRuntime().availableProcessors();

    /**
     * Cell数组，在竞争时使用，长度为2的幂次方
     */
    transient volatile Cell[] cells;

    /**
     * Base 值, 主要用于无竞争条件下, 也作为table竞争初始化失败的后备方案。Updated via CAS.
     */
    transient volatile long base;

    /**
     * 自旋锁 (locked via CAS) 用于 resizing 或者创建cells.
     */
    transient volatile int cellsBusy;

    Striped64() {
    }

    /**
     * CAS 操作base字段
     */
    final boolean casBase(long cmp, long val) {
        return UNSAFE.compareAndSwapLong(this, BASE, cmp, val);
    }

    /**
     * CAS 操作cellsBusy字段从0到1，相当于获取锁。
     */
    final boolean casCellsBusy() {
        return UNSAFE.compareAndSwapInt(this, CELLSBUSY, 0, 1);
    }

    /**
     * 返回当前线程的探针值，由于包访问限制，从ThreadLocalRandom中复制。
     *
     * probe是线程的探针哈希值，作用是hash线程，将线程和数组中的不用元素对应起来，尽量避免线程争用同一数组元素。探针哈希值和map里使用的哈希值
     * 的区别是，当线程发生数组元素争用后，可以改变线程的探针哈希值，让线程去使用另一个数组元素，而 map 中 key 对象的哈希值，由于有定位 value
     * 的需求，所以它是一定不能变的。
     */
    static final int getProbe() {
        return UNSAFE.getInt(Thread.currentThread(), PROBE);
    }

    /**
     * 重新伪随机probe，并记录当前线程的探针哈希值（probe）。 由于包访问限制，从ThreadLocalRandom中复制。
     */
    static final int advanceProbe(int probe) {
        probe ^= probe << 13;   // xorshift
        probe ^= probe >>> 17;
        probe ^= probe << 5;
        UNSAFE.putInt(Thread.currentThread(), PROBE, probe);
        return probe;
    }

    /**
     * 累加器实现，处理涉及初始化、resing、创建新cell、和/或竞争的更新。
     *
     * @param x the value
     * @param fn 更新函数, null为 add .
     * @param wasUncontended 是否非竞争状态，调用前CAS失败则为false.
     */
    final void longAccumulate(long x, LongBinaryOperator fn, boolean wasUncontended) {
        //获取线程探针hash值
        int h;
        if ((h = getProbe()) == 0) {
            ThreadLocalRandom.current(); // force initialization
            h = getProbe();
            wasUncontended = true;
        }

        boolean collide = false;                // 最后的Cell不为空则为true,也用于控制扩容，false重试。
        for (;;) {
            Cell[] as; Cell a; int n; long v;
            //表已初始化
            if ((as = cells) != null && (n = as.length) > 0) {
                //如果探针hash映射到的cell为空，初始化cell并关联到槽。
                if ((a = as[(n - 1) & h]) == null) {
                    if (cellsBusy == 0) {       // Try to attach new Cell
                        Cell r = new Cell(x);   // Optimistically create
                        if (cellsBusy == 0 && casCellsBusy()) {
                            boolean created = false;
                            try {               // Recheck under lock
                                Cell[] rs; int m, j;
                                if ((rs = cells) != null &&
                                        (m = rs.length) > 0 &&
                                        rs[j = (m - 1) & h] == null) {
                                    rs[j] = r;
                                    created = true;
                                }
                            } finally {
                                cellsBusy = 0;
                            }
                            if (created)
                                break;          //成功创建cell,退出
                            continue;           // Slot is now non-empty
                        }
                    }
                    collide = false;
                }
                // CAS 已经失败
                else if (!wasUncontended)
                    wasUncontended = true;      // Continue after rehash
                // 在当前cell上尝试更新
                else if (a.cas(v = a.value, ((fn == null) ? v + x :
                        fn.applyAsLong(v, x))))
                    break;
                //表大小达到最大值，不会再尝试，重新hash,尝试其他槽。
                else if (n >= NCPU || cells != as)
                    collide = false;            // At max size or stale
                else if (!collide)
                    collide = true;
                //有竞争，可以获取锁，则扩容。
                else if (cellsBusy == 0 && casCellsBusy()) {
                    try {
                        if (cells == as) {      // Expand table unless stale
                            Cell[] rs = new Cell[n << 1];
                            for (int i = 0; i < n; ++i)
                                rs[i] = as[i];
                            cells = rs;
                        }
                    } finally {
                        cellsBusy = 0;
                    }
                    collide = false;
                    continue;                   // Retry with expanded table
                }

                //无法获取锁，重新hash,尝试其他cell
                h = advanceProbe(h);
            }
            //表未初始化，进行加锁并初始化
            else if (cellsBusy == 0 && cells == as && casCellsBusy()) {
                boolean init = false;
                try {                           // Initialize table
                    if (cells == as) {
                        Cell[] rs = new Cell[2];
                        rs[h & 1] = new Cell(x);
                        cells = rs;
                        init = true;
                    }
                } finally {
                    cellsBusy = 0;
                }
                if (init)
                    break;
            }
            //表未被初始化，可能正在初始化中，回退使用base。
            else if (casBase(v = base, ((fn == null) ? v + x :
                    fn.applyAsLong(v, x))))
                break;                          // Fall back on using base
        }
    }

    /**
     * double累加器，和long类似。
     */
    final void doubleAccumulate(double x, DoubleBinaryOperator fn,
                                boolean wasUncontended) {
        int h;
        if ((h = getProbe()) == 0) {
            ThreadLocalRandom.current(); // force initialization
            h = getProbe();
            wasUncontended = true;
        }
        boolean collide = false;                // True if last slot nonempty
        for (;;) {
            Cell[] as; Cell a; int n; long v;
            if ((as = cells) != null && (n = as.length) > 0) {
                if ((a = as[(n - 1) & h]) == null) {
                    if (cellsBusy == 0) {       // Try to attach new Cell
                        Cell r = new Cell(Double.doubleToRawLongBits(x));
                        if (cellsBusy == 0 && casCellsBusy()) {
                            boolean created = false;
                            try {               // Recheck under lock
                                Cell[] rs; int m, j;
                                if ((rs = cells) != null &&
                                        (m = rs.length) > 0 &&
                                        rs[j = (m - 1) & h] == null) {
                                    rs[j] = r;
                                    created = true;
                                }
                            } finally {
                                cellsBusy = 0;
                            }
                            if (created)
                                break;
                            continue;           // Slot is now non-empty
                        }
                    }
                    collide = false;
                }
                else if (!wasUncontended)       // CAS already known to fail
                    wasUncontended = true;      // Continue after rehash
                else if (a.cas(v = a.value,
                        ((fn == null) ?
                                Double.doubleToRawLongBits
                                        (Double.longBitsToDouble(v) + x) :
                                Double.doubleToRawLongBits
                                        (fn.applyAsDouble
                                                (Double.longBitsToDouble(v), x)))))
                    break;
                else if (n >= NCPU || cells != as)
                    collide = false;            // At max size or stale
                else if (!collide)
                    collide = true;
                else if (cellsBusy == 0 && casCellsBusy()) {
                    try {
                        if (cells == as) {      // Expand table unless stale
                            Cell[] rs = new Cell[n << 1];
                            for (int i = 0; i < n; ++i)
                                rs[i] = as[i];
                            cells = rs;
                        }
                    } finally {
                        cellsBusy = 0;
                    }
                    collide = false;
                    continue;                   // Retry with expanded table
                }
                h = advanceProbe(h);
            }
            else if (cellsBusy == 0 && cells == as && casCellsBusy()) {
                boolean init = false;
                try {                           // Initialize table
                    if (cells == as) {
                        Cell[] rs = new Cell[2];
                        rs[h & 1] = new Cell(Double.doubleToRawLongBits(x));
                        cells = rs;
                        init = true;
                    }
                } finally {
                    cellsBusy = 0;
                }
                if (init)
                    break;
            }
            else if (casBase(v = base,
                    ((fn == null) ?
                            Double.doubleToRawLongBits
                                    (Double.longBitsToDouble(v) + x) :
                            Double.doubleToRawLongBits
                                    (fn.applyAsDouble
                                            (Double.longBitsToDouble(v), x)))))
                break;                          // Fall back on using base
        }
    }


    // Unsafe mechanics
    private static final sun.misc.Unsafe UNSAFE;
    private static final long BASE;
    private static final long CELLSBUSY;
    private static final long PROBE;
    static {
        try {
            UNSAFE = sun.misc.Unsafe.getUnsafe();
            Class<?> sk = Striped64.class;
            BASE = UNSAFE.objectFieldOffset
                    (sk.getDeclaredField("base"));
            CELLSBUSY = UNSAFE.objectFieldOffset
                    (sk.getDeclaredField("cellsBusy"));
            Class<?> tk = Thread.class;
            PROBE = UNSAFE.objectFieldOffset
                    (tk.getDeclaredField("threadLocalRandomProbe"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }
}
