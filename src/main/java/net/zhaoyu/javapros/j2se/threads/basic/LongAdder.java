package net.zhaoyu.javapros.j2se.threads.basic;

import java.io.Serializable;

/**
 * Long增加器。继承自Striped64，实现对long的增加操作。该类对比AtomicLong，多线程操作时，更适合收集sum等统计信息，而不是细粒度的同步控制。
 * 如统计ConcurrentHashMap中的size。在高竞争条件下，该类吞吐量显著提高，而占用了更高的空间消耗。
 */
public class LongAdder extends Striped64 implements Serializable {

    public LongAdder() {
    }

     /**
     * 加x操作，非竞争条件下，直接cas，竞争条件下使用Striped64的累加器
     */
    public void add(long x) {
        Cell[] as; long b, v; int m; Cell a;
        if ((as = cells) != null || !casBase(b = base, b + x)) {
            boolean uncontended = true;
            if (as == null || (m = as.length - 1) < 0 ||
                (a = as[getProbe() & m]) == null ||
                !(uncontended = a.cas(v = a.value, v + x)))
                longAccumulate(x, null, uncontended);
        }
    }

     /**
     * 返回当前总和。在没有竞争情况下，返回正确结果，在竞争情况下，并发更新可能不会包含在内。
     */
    public long sum() {
        Cell[] as = cells; Cell a;
        long sum = base;
        if (as != null) {
            for (int i = 0; i < as.length; ++i) {
                if ((a = as[i]) != null)
                    sum += a.value;
            }
        }
        return sum;
    }


    @Override
    public long longValue() {
        return sum();
    }
    @Override
    public int intValue() {
        return (int)sum();
    }
    @Override
    public float floatValue() {
        return (float)sum();
    }
    @Override
    public double doubleValue() {
        return (double)sum();
    }
}
