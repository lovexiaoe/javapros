package net.zhaoyu.javapros.j2se.threads.basic;


import java.io.ObjectStreamField;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
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

    /* ---------- 常亮 ---------- */

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
     * 表的容量最小为多少时， 增加元素时桶转换为树结构，小于最小容量，增加元素超过TREEIFY_THRESHOLD，表进行resize操作。即默认table大于64时，
     * 才会有树结构生成。
     * 该值至少是4 * TREEIFY_THRESHOLD，避免resizing和树阈值的冲突。
     */
    static final int MIN_TREEIFY_CAPACITY = 64;

    private static final int MIN_TRANSFER_STRIDE = 16;

    private static int RESIZE_STAMP_BITS = 16;

    public static final int  MAX_RESIZERS=(1 << (32 - RESIZE_STAMP_BITS)) - 1;

    public static final int RESIZE_STAMP_SHIFT = 32 - RESIZE_STAMP_BITS;

    //用于hash算法的变量
    static final int MOVED     = -1; // forwarding 节点的hash值，代表容器正在扩容，当前节点的数据已经被移动到扩容后的数组中。
    static final int TREEBIN   = -2; // 树的根节点的hash值，代表当前槽位上的节点采用红黑树结构存储。
    static final int RESERVED  = -3; // hash for transient reservations，代表该节点正在进行函数式运算，节点值还未最终确定。
    static final int HASH_BITS = 0x7fffffff; // 最高为为0，其他位为1。

    //cpu数量
    static final int NCPU = Runtime.getRuntime().availableProcessors();

    /** 兼容老版本序列化 */
    private static final ObjectStreamField[] serialPersistentFields = {
            new ObjectStreamField("segments", java.util.concurrent.ConcurrentHashMap.Segment[].class),
            new ObjectStreamField("segmentMask", Integer.TYPE),
            new ObjectStreamField("segmentShift", Integer.TYPE)
    };

    /* ---------- Node ---------- */

    /**
     * 链表节点对象，hash为负数的节点是特殊节点。
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
         * 对 map.get() 提供支撑，链表情况下用不到，当前桶位变成 treebin  fwd节点会用到
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
     * 表初始化和resizing的控制， 0为默认，为负数时，表被初始化或者resize中。 初始化为-1, resize为-(1 + 激活的resizing线程数量).
     * 其他情况：当表为未初始化，表示创建时表需要初始化的大小 . 初始化完成, 表示下一次resize需要的初始化大小.
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

    /* ---------- 公共操作 ---------- */

    public ConcurrentHashMap() {
    }


    /**
     * 创建有初始化容量为initialCapacity的map,容量预估为至少两倍的initialCapacity
     */
    public ConcurrentHashMap(int initialCapacity) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException();
        int cap = ((initialCapacity >= (MAXIMUM_CAPACITY >>> 1)) ?
                MAXIMUM_CAPACITY :
                tableSizeFor(initialCapacity + (initialCapacity >>> 1) + 1));
        this.sizeCtl = cap;
    }

    public ConcurrentHashMap(Map<? extends K, ? extends V> m) {
        this.sizeCtl = DEFAULT_CAPACITY;
        putAll(m);
    }

    public ConcurrentHashMap(int initialCapacity, float loadFactor) {
        this(initialCapacity, loadFactor, 1);
    }

    /**
     * concurrencyLevel 用来计算 sizeCtl 的大小。 影响map的bin长度，bin长度决定了并发级别。
     */
    public ConcurrentHashMap(int initialCapacity,
                             float loadFactor, int concurrencyLevel) {
        if (!(loadFactor > 0.0f) || initialCapacity < 0 || concurrencyLevel <= 0)
            throw new IllegalArgumentException();
        if (initialCapacity < concurrencyLevel)   // Use at least as many bins
            initialCapacity = concurrencyLevel;   // as estimated threads
        long size = (long)(1.0 + (long)initialCapacity / loadFactor);
        int cap = (size >= (long)MAXIMUM_CAPACITY) ?
                MAXIMUM_CAPACITY : tableSizeFor((int)size);
        this.sizeCtl = cap;
    }

    /* ---------- map的原有方法 ---------- */

    public int size() {
        long n = sumCount();
        return ((n < 0L) ? 0 :
                (n > (long)Integer.MAX_VALUE) ? Integer.MAX_VALUE :
                        (int)n);
    }

    public boolean isEmpty() {
        return sumCount() <= 0L;
    }

    public V get(Object key) {
        Node<K,V>[] tab; Node<K,V> e, p; int n, eh; K ek;
        int h = spread(key.hashCode());
        if ((tab = table) != null && (n = tab.length) > 0 &&
                (e = tabAt(tab, (n - 1) & h)) != null) {
            //如果在table中找到，直接返回。
            if ((eh = e.hash) == h) {
                if ((ek = e.key) == key || (ek != null && key.equals(ek)))
                    return e.val;
            }
            //eh小于0，说明是特殊节点，即树节点。
            else if (eh < 0)
                return (p = e.find(h, key)) != null ? p.val : null;
            //从链表中查找
            while ((e = e.next) != null) {
                if (e.hash == h &&
                        ((ek = e.key) == key || (ek != null && key.equals(ek))))
                    return e.val;
            }
        }
        return null;
    }

    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    public boolean containsValue(Object value) {
        if (value == null)
            throw new NullPointerException();
        Node<K,V>[] t;
        if ((t = table) != null) {
            Traverser<K,V> it = new Traverser<K,V>(t, t.length, 0, t.length);
            for (Node<K,V> p; (p = it.advance()) != null; ) {
                V v;
                if ((v = p.val) == value || (v != null && value.equals(v)))
                    return true;
            }
        }
        return false;
    }

    /* ---------- 特殊的Node ---------- */

    /**
     * 在transfer操作时，会在红黑树bin中插入一个ForwardingNode节点。
     */
    static final class ForwardingNode<K,V> extends Node<K,V> {
        final Node<K,V>[] nextTable;
        ForwardingNode(Node<K,V>[] tab) {
            super(MOVED, null, null, null);
            this.nextTable = tab;
        }

        Node<K,V> find(int h, Object k) {
            // loop to avoid arbitrarily deep recursion on forwarding nodes
            outer: for (Node<K,V>[] tab = nextTable;;) {
                Node<K,V> e; int n;
                if (k == null || tab == null || (n = tab.length) == 0 ||
                        (e = tabAt(tab, (n - 1) & h)) == null)
                    return null;
                for (;;) {
                    int eh; K ek;
                    if ((eh = e.hash) == h &&
                            ((ek = e.key) == k || (ek != null && k.equals(ek))))
                        return e;
                    if (eh < 0) {
                        if (e instanceof ForwardingNode) {
                            tab = ((ForwardingNode<K,V>)e).nextTable;
                            continue outer;
                        }
                        else
                            return e.find(h, k);
                    }
                    if ((e = e.next) == null)
                        return null;
                }
            }
        }
    }

    /* ---------- table 遍历 ---------- */

    /**
     * 遍历的实现难度主要是在于遍历的过程中元素可能会新增或者删除，或者遇到扩容的情况。分情况分析：
     *   1,遍历时容器没有变化
     *   2,遍历时容器元素有新增或者删除
     *   3,遍历时容器正在扩容
     */

    /**
     * 用于为traverser记录当前tab,表的长度，和当前遍历的index。
     * 在resizing时，traverser需要在处理当前tab之前处理一段forwarded tab的数据。
     */
    static final class TableStack<K,V> {
        int length;
        int index;
        Node<K,V>[] tab;
        TableStack<K,V> next;
    }

    /**
     * iterators and spliterators 的基类。
     * 通常情况下，traverser一个个bin地进行遍历。但是表已经resized。那么后续的步骤必须同时遍历当前index下的bin和（index+baseSize）的两个bin。
     * 以此类推，以便进一步的resizings。
     * 为了特意处理用户跨线程迭代器共享的可能性。如果对表读取的一系列检查失败，则终止迭代。
     */
    static class Traverser<K,V> {
        Node<K,V>[] tab;        // 当前table,resized时会更行。
        Node<K,V> next;         // 要使用的下一个entry节点。
        TableStack<K,V> stack, spare; // 当碰到 ForwardingNode ，保存或者恢复
        int index;              // 要使用的下一个桶
        int baseIndex;          // table初始时的起始索引
        int baseLimit;          // table初始时的终止索引。
        final int baseSize;     // table初始时的大小

        Traverser(Node<K,V>[] tab, int size, int index, int limit) {
            this.tab = tab;
            this.baseSize = size;
            this.baseIndex = this.index = index;
            this.baseLimit = limit;
            this.next = null;
        }

        /**
         * 如果有下一个可用节点，返回该节点，否则返回null。方法会访问自Iterator构建后可达的节点。可能会丢失一些在访问bin之后添加到bin的内容。
         */
        /* 这里如果遇到扩容，存储当前状态pushState(t, i, n)，等到扩容完成后，取出状态recoverState(n) */
        final Node<K,V> advance() {
            Node<K,V> e;
            if ((e = next) != null) {
                // 如果已经进入了一个非空的hash桶，直接尝试获取桶中的下一个节点。
                e = e.next;
            }
            for (;;) {
                Node<K,V>[] t; int i, n;  // must use locals in checks
                //节点不为null，直接返回，更新next。
                if (e != null)
                    return next = e;
                /* 初始索引 >= 终止索引 || tab数组为空 || tab数组长度 <= 下界 || index < 0，没有新桶，返回 null  */
                if (baseIndex >= baseLimit || (t = tab) == null ||
                    (n = t.length) <= (i = index) || i < 0)
                    return next = null;
                /* 桶存在且hash<0,若此时为链表节点，e已经复制完毕。*/
                if ((e = tabAt(t, i)) != null && e.hash < 0) {
                    //是否为forwarding节点，表示正在扩容。
                    if (e instanceof ForwardingNode) {
                        tab = ((ForwardingNode<K,V>)e).nextTable;
                        e = null;
                        pushState(t, i, n); //保存当前遍历状态
                        continue;
                    }
                    //如果是树节点，返回树的首节点。
                    else if (e instanceof TreeBin)
                        e = ((TreeBin<K,V>)e).first;
                    else
                        e = null;
                }
                /* 弹出存储状态 */
                if (stack != null)
                    recoverState(n);
                else if ((index = i + baseSize) >= n)
                    index = ++baseIndex; // visit upper slots if present
            }
        }

        /**
         * 当碰到forwardingNode时，保存当前遍历状态到stack中，spare辅助，预先建立一个对象，用于减少new对象。
         */
        private void pushState(Node<K,V>[] t, int i, int n) {
            TableStack<K,V> s = spare;  // spare用于复用，减少new对象。
            if (s != null)
                spare = s.next;
            else
                s = new TableStack<K,V>();
            s.tab = t;
            s.length = n;
            s.index = i;
            s.next = stack;
            stack = s;
        }

        /**
         * 恢复存储的遍历状态,从stack中取出状态到traverser中。
         */
        private void recoverState(int n) {
            TableStack<K,V> s; int len;
            while ((s = stack) != null && (index += (len = s.length)) >= n) {
                n = len;
                index = s.index;
                tab = s.tab;
                s.tab = null;
                TableStack<K,V> next = s.next;
                s.next = spare; // save for reuse
                stack = next;
                spare = s;
            }
            if (s == null && (index += baseSize) >= n)
                index = ++baseIndex;
        }
    }

    /**
     * Base of key, value, and entry Iterators.为支持iterator.remove添加需要的字段。
     */
    static class BaseIterator<K,V> extends Traverser<K,V> {
        final ConcurrentHashMap<K,V> map;
        Node<K,V> lastReturned;
        BaseIterator(Node<K,V>[] tab, int size, int index, int limit,
                    ConcurrentHashMap<K,V> map) {
            super(tab, size, index, limit);
            this.map = map;
            advance();
        }

        public final boolean hasNext() { return next != null; }
        public final boolean hasMoreElements() { return next != null; }

        public final void remove() {
            Node<K,V> p;
            if ((p = lastReturned) == null)
                throw new IllegalStateException();
            lastReturned = null;
            map.replaceNode(p.key, null, null);
        }
    }

    static final class KeyIterator<K,V> extends BaseIterator<K,V>
        implements Iterator<K>, Enumeration<K> {
        KeyIterator(Node<K,V>[] tab, int index, int size, int limit,
                    ConcurrentHashMap<K,V> map) {
            super(tab, index, size, limit, map);
        }

        public final K next() {
            Node<K,V> p;
            if ((p = next) == null)
                throw new NoSuchElementException();
            K k = p.key;
            lastReturned = p;
            advance();
            return k;
        }

        public final K nextElement() { return next(); }
    }

    static final class ValueIterator<K,V> extends BaseIterator<K,V>
        implements Iterator<V>, Enumeration<V> {
        ValueIterator(Node<K,V>[] tab, int index, int size, int limit,
                      ConcurrentHashMap<K,V> map) {
            super(tab, index, size, limit, map);
        }

        public final V next() {
            Node<K,V> p;
            if ((p = next) == null)
                throw new NoSuchElementException();
            V v = p.val;
            lastReturned = p;
            advance();
            return v;
        }

        public final V nextElement() { return next(); }
    }

    static final class EntryIterator<K,V> extends BaseIterator<K,V>
        implements Iterator<Entry<K,V>> {
        EntryIterator(Node<K,V>[] tab, int index, int size, int limit,
                      ConcurrentHashMap<K,V> map) {
            super(tab, index, size, limit, map);
        }

        public final Map.Entry<K,V> next() {
            Node<K,V> p;
            if ((p = next) == null)
                throw new NoSuchElementException();
            K k = p.key;
            V v = p.val;
            lastReturned = p;
            advance();
            return new MapEntry<K,V>(k, v, map);
        }
    }

    static final class MapEntry<K,V> implements Map.Entry<K,V> {
        final K key; // non-null
        V val;       // non-null
        final ConcurrentHashMap<K,V> map;
        MapEntry(K key, V val, ConcurrentHashMap<K,V> map) {
            this.key = key;
            this.val = val;
            this.map = map;
        }
        public K getKey()        { return key; }
        public V getValue()      { return val; }
        public int hashCode()    { return key.hashCode() ^ val.hashCode(); }
        public String toString() { return key + "=" + val; }

        public boolean equals(Object o) {
            Object k, v; Map.Entry<?,?> e;
            return ((o instanceof Map.Entry) &&
                    (k = (e = (Map.Entry<?,?>)o).getKey()) != null &&
                    (v = e.getValue()) != null &&
                    (k == key || k.equals(key)) &&
                    (v == val || v.equals(val)));
        }

        /**
         * Sets our entry's value and writes through to the map. The
         * value to return is somewhat arbitrary here. Since we do not
         * necessarily track asynchronous changes, the most recent
         * "previous" value could be different from what we return (or
         * could even have been removed, in which case the put will
         * re-establish). We do not and cannot guarantee more.
         */
        public V setValue(V value) {
            if (value == null) throw new NullPointerException();
            V v = val;
            val = value;
            map.put(key, value);
            return v;
        }
    }

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

    /* ----------- 表元素访问 ---------- */

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
