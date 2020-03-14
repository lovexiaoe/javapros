package net.zhaoyu.javapros.j2se.threads.basic;


/**
 * ConcurrentHashMap支持完全的并发读取和高性能的更新操作。它实现了所有HashTable的对应功能，
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
public class ConcurrentHashMapTest {
}
