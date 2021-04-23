package net.zhaoyu.javapros.j2se.threads.basic;


import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
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

    static final int TREEIFY_THRESHOLD = 8;


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
}
