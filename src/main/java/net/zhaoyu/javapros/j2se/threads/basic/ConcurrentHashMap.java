package net.zhaoyu.javapros.j2se.threads.basic;


import java.io.ObjectStreamField;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 它遵从所有HashTable的功能规范，并且所有操作都是线程安全的。获取操作不需要锁定，也不会锁定整个表来阻止任何访问。
 * 获取（get）操作不会阻塞，可能和更新(put/remove)操作重叠发生。对于聚合操作（putAll/clear）,当前get可能只反映部分元素的改变。
 * 类似的，迭代器，拆分器，枚举都反映某一时刻hash表的状态。
 *
 * iterators被设计为在某个时间内只有一个线程使用。聚合状态函数（size,isEmpty,containsValue）只有在其他线程没有并发更新时才有用。
 * 否则在其他情况下返回的即时状态不是精确的。
 *
 * ConcurrentHashMap采用分段锁思想。主干是一个segment数组，segment继承自ReentrantLock，是一种可
 * 重入锁。一个segment类似一个hashtable。一个segment里面维护了一个HashEntry数组。
 *
 * 获取时不需要枷锁，修改时，锁定修改所在的segment。
 *
 * 默认的并发等级ConcurrentLevel为16，理论上允许16个线程并发访问。
 *
 * Segment数组的大小一定是大于等于ConcurrentLevel的最小的2的幂次方。比如ConcurrentLevel是17，那么
 * Segment数组的大小为32。这是因为需要通过按“位与”的hash算法定位Segment的index。
 *
 * 有些方法需要跨段，比如size()和containsValue()，它们可能需要锁定整个表而而不仅仅是某个段，
 * 这需要按顺序锁定所有段，操作完毕后，又按顺序释放所有段的锁。
 */
public class ConcurrentHashMap<K,V> extends AbstractMap<K,V> implements ConcurrentMap<K,V>, Serializable {

    /**
     * 最大容量
     */
    private static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     * 默认容量
     */
    private static final int DEFAULT_CAPACITY=16;

    /**
     * 数组最大长度
     */
    static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    /**
     * 默认的并发级别。没用，但是为了兼容前面的版本。
     */
    private static final int DEFAULT_CONCURRENCY_LEVEL = 16;

    /**
     * 加载因子，覆盖该参数只影响初始化容量。
     */
    private static final float LOAD_FACTOR = 0.75f;

    /**
     * 箱子由list转换为tree的阈值，该值必须大于2，并且至少是8
     */
    static final int TREEIFY_THRESHOLD = 8;

    /**
     * 在resize操作中，由tree转换为list的阈值，应该小于TREEIFY_THRESHOLD，最大是6，以匹配删除时的收缩检测。
     */
    static final int UNTREEIFY_THRESHOLD = 6;

    /**
     * 红黑树的最小容量。该值至少是4 * TREEIFY_THRESHOLD，避免resizing和树阈值的冲突。
     */
    static final int MIN_TREEIFY_CAPACITY = 64;

    private static final int MIN_TRANSFER_STRIDE = 16;

    private static int RESIZE_STAMP_BITS = 16;

    public static final int  MAX_RESIZERS=(1 << (32 - RESIZE_STAMP_BITS)) - 1;

    public static final int RESIZE_STAMP_SHIFT = 32 - RESIZE_STAMP_BITS;

    //用于hash算法的变量
    static final int MOVED     = -1; // hash for forwarding nodes
    static final int TREEBIN   = -2; // hash for roots of trees
    static final int RESERVED  = -3; // hash for transient reservations
    static final int HASH_BITS = 0x7fffffff; // 最高为为0，其他位为1。

    //cpu数量
    static final int NCPU = Runtime.getRuntime().availableProcessors();

    /** 兼容序列化 */
    private static final ObjectStreamField[] serialPersistentFields = {
            new ObjectStreamField("segments", java.util.concurrent.ConcurrentHashMap.Segment[].class),
            new ObjectStreamField("segmentMask", Integer.TYPE),
            new ObjectStreamField("segmentShift", Integer.TYPE)
    };

    /**
     * 节点对象
     * @param <K>
     * @param <V>
     */
    static class Node<K,V> implements Map.Entry<K,V> {
        final int hash;
        final K key;
        volatile V val;
        volatile Node<K,V> next;

        Node(int hash, K key, V val, Node<K,V> next) {
            this.hash = hash;
            this.key = key;
            this.val = val;
            this.next = next;
        }

        public final K getKey()       { return key; }
        public final V getValue()     { return val; }
        public final int hashCode()   { return key.hashCode() ^ val.hashCode(); }
        public final String toString(){ return key + "=" + val; }
        public final V setValue(V value) {
            throw new UnsupportedOperationException();
        }

        public final boolean equals(Object o) {
            Object k, v, u; Map.Entry<?,?> e;
            return ((o instanceof Map.Entry) &&
                    (k = (e = (Map.Entry<?,?>)o).getKey()) != null &&
                    (v = e.getValue()) != null &&
                    (k == key || k.equals(key)) &&
                    (v == (u = val) || v.equals(u)));
        }

        /**
         * 对 map.get() 提供支撑.
         */
        Node<K,V> find(int h, Object k) {
            Node<K,V> e = this;
            if (k != null) {
                do {
                    K ek;
                    if (e.hash == h &&
                            ((ek = e.key) == k || (ek != null && k.equals(ek))))
                        return e;
                } while ((e = e.next) != null);
            }
            return null;
        }
    }


    /* ---------------- 静态方法 -------------- */

    /**
     * hash方法。对对象的hashcode进行扩展
     * @param h
     * @return
     */
    static final int spread(int h) {
        return (h ^ (h >>> 16)) & HASH_BITS;
    }

    /**
     * map的长度。获取int最大的2的幂次方，如5，返回2的3次方8。
     * @param c
     * @return
     */
    private static final int tableSizeFor(int c) {
        int n = c - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    /**
     * 返回 实际实现Comparable接口的class，没有实现则返回null。
     * @param x
     * @return
     */
    static Class<?> comparableClassFor(Object x) {
        if (x instanceof Comparable) {
            Class<?> c; Type[] ts, as; Type t; ParameterizedType p;
            if ((c = x.getClass()) == String.class) // bypass checks
                return c;
            if ((ts = c.getGenericInterfaces()) != null) {
                for (int i = 0; i < ts.length; ++i) {
                    if (((t = ts[i]) instanceof ParameterizedType) &&
                            ((p = (ParameterizedType)t).getRawType() ==
                                    Comparable.class) &&
                            (as = p.getActualTypeArguments()) != null &&
                            as.length == 1 && as[0] == c) // type arg is c
                        return c;
                }
            }
        }
        return null;
    }

    /**
     * Returns k.compareTo(x) if x matches kc (k's screened comparable
     * class), else 0.
     */
    @SuppressWarnings({"rawtypes","unchecked"}) // 比较两个对象
    static int compareComparables(Class<?> kc, Object k, Object x) {
        return (x == null || x.getClass() != kc ? 0 :
                ((Comparable)k).compareTo(x));
    }

    /* ---------- 字段 -------------*/
    /**
     * 节点数组，首次插入时，延迟加载，size永远是2的幂次方。通过iterator直接访问。
     */
    transient volatile Node<K,V>[] table;

    /**
     * 用于扩展的新table; 当 resizing时不为null。
     */
    private transient volatile Node<K,V>[] nextTable;

    /**
     * 基准计数值, 主要在没有竞争时使用，但是也作为表初始化竞争时的后备。 使用 CAS 进行更新.
     */
    private transient volatile long baseCount;

    /**
     * 表初始化和resizing的控制，为负数时，表被初始化或者resize中。 -1 表示初始化, -(1 + 激活的resizing线程数量). 0为默认，
     * 当表为空时，为创建时的初始化表大小 . 初始化后, 为resize 后table元素的计数.
     */
    private transient volatile int sizeCtl;

    /**
     * todo:注释
     */
    private transient volatile int transferIndex;

    /**
     * 当resizing或者创建CounterCells时使用的自旋锁（用CAS锁定）
     */
    private transient volatile int cellsBusy;

    /**
     * CounterCell表，用于计算map中的元素个数，每个cell中都记录了一个值，map元素的个数就是每个cell中的值累加。
     * 非空时，长度为2的幂次方。
     */
    private transient volatile CounterCell[] counterCells;

    // views
    private transient KeySetView<K,V> keySet;
    private transient ValuesView<K,V> values;
    private transient EntrySetView<K,V> entrySet;


    /* ---------------- Counter support -------------- */

    /**
     * 用于分散计数的辅助cell. Adapted from LongAdder
     * and Striped64.  See their internal docs for explanation.
     *
     * java8 @sun.misc.Contended 注解让CPU独占一个缓存行。
     */
    @sun.misc.Contended static final class CounterCell {
        volatile long value;
        CounterCell(long x) { value = x; }
    }

    final long sumCount() {
        CounterCell[] as = counterCells; CounterCell a;
        long sum = baseCount;
        if (as != null) {
            for (int i = 0; i < as.length; ++i) {
                if ((a = as[i]) != null)
                    sum += a.value;
            }
        }
        return sum;
    }

    // See LongAdder version for explanation
    private final void fullAddCount(long x, boolean wasUncontended) {
        int h;
        if ((h = ThreadLocalRandom.getProbe()) == 0) {
            ThreadLocalRandom.localInit();      // force initialization
            h = ThreadLocalRandom.getProbe();
            wasUncontended = true;
        }
        boolean collide = false;                // True if last slot nonempty
        for (;;) {
            CounterCell[] as; CounterCell a; int n; long v;
            if ((as = counterCells) != null && (n = as.length) > 0) {
                if ((a = as[(n - 1) & h]) == null) {
                    if (cellsBusy == 0) {            // Try to attach new Cell
                        CounterCell r = new CounterCell(x); // Optimistic create
                        if (cellsBusy == 0 &&
                            U.compareAndSwapInt(this, CELLSBUSY, 0, 1)) {
                            boolean created = false;
                            try {               // Recheck under lock
                                CounterCell[] rs; int m, j;
                                if ((rs = counterCells) != null &&
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
                else if (U.compareAndSwapLong(a, CELLVALUE, v = a.value, v + x))
                    break;
                else if (counterCells != as || n >= NCPU)
                    collide = false;            // At max size or stale
                else if (!collide)
                    collide = true;
                else if (cellsBusy == 0 &&
                         U.compareAndSwapInt(this, CELLSBUSY, 0, 1)) {
                    try {
                        if (counterCells == as) {// Expand table unless stale
                            CounterCell[] rs = new CounterCell[n << 1];
                            for (int i = 0; i < n; ++i)
                                rs[i] = as[i];
                            counterCells = rs;
                        }
                    } finally {
                        cellsBusy = 0;
                    }
                    collide = false;
                    continue;                   // Retry with expanded table
                }
                h = ThreadLocalRandom.advanceProbe(h);
            }
            else if (cellsBusy == 0 && counterCells == as &&
                     U.compareAndSwapInt(this, CELLSBUSY, 0, 1)) {
                boolean init = false;
                try {                           // Initialize table
                    if (counterCells == as) {
                        CounterCell[] rs = new CounterCell[2];
                        rs[h & 1] = new CounterCell(x);
                        counterCells = rs;
                        init = true;
                    }
                } finally {
                    cellsBusy = 0;
                }
                if (init)
                    break;
            }
            else if (U.compareAndSwapLong(this, BASECOUNT, v = baseCount, v + x))
                break;                          // Fall back on using base
        }
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        return null;
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {

    }

    @Override
    public boolean remove(Object key, Object value) {
        return false;
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        return false;
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {

    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        return null;
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return null;
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return null;
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return null;
    }

    /*
     * 以下方法为用于访问table中的元素或resizing过程中新table的元素，称为不稳定（Volatile）方法。用户需要对tab参数做null检查，检测tab的
     * 长度大于0
     */
    static final <K,V> Node<K,V> tabAt(Node<K,V>[] tab, int i) {
        return (Node<K,V>)U.getObjectVolatile(tab, ((long)i << ASHIFT) + ABASE);
    }

    static final <K,V> boolean casTabAt(Node<K,V>[] tab, int i,
                                        Node<K,V> c, Node<K,V> v) {
        return U.compareAndSwapObject(tab, ((long)i << ASHIFT) + ABASE, c, v);
    }

    static final <K,V> void setTabAt(Node<K,V>[] tab, int i, Node<K,V> v) {
        U.putObjectVolatile(tab, ((long)i << ASHIFT) + ABASE, v);
    }

    // Unsafe mechanics
    private static final sun.misc.Unsafe U;
    private static final long SIZECTL;
    private static final long TRANSFERINDEX;
    private static final long BASECOUNT;
    private static final long CELLSBUSY;
    private static final long CELLVALUE;
    private static final long ABASE;
    private static final int ASHIFT;

    static {
        try {
            U = sun.misc.Unsafe.getUnsafe();
            Class<?> k = ConcurrentHashMap.class;
            SIZECTL = U.objectFieldOffset
                (k.getDeclaredField("sizeCtl"));
            TRANSFERINDEX = U.objectFieldOffset
                (k.getDeclaredField("transferIndex"));
            BASECOUNT = U.objectFieldOffset
                (k.getDeclaredField("baseCount"));
            CELLSBUSY = U.objectFieldOffset
                (k.getDeclaredField("cellsBusy"));
            Class<?> ck = CounterCell.class;
            CELLVALUE = U.objectFieldOffset
                (ck.getDeclaredField("value"));
            Class<?> ak = Node[].class;
            ABASE = U.arrayBaseOffset(ak);
            int scale = U.arrayIndexScale(ak);
            if ((scale & (scale - 1)) != 0)
                throw new Error("data type scale not a power of two");
            ASHIFT = 31 - Integer.numberOfLeadingZeros(scale);
        } catch (Exception e) {
            throw new Error(e);
        }
    }
}
