package com.zhaoyu.collections.maps;

/**
 * HashTable继承Dictionary类。
 * Dictionary已经过时，新的应用应该实现Map接口。
 *
 * HashTable也是一个hash表，任何非null对象都可以作为key或者value。作为key的对象必须实现hashCode方法和equals方法。
 * HashTable有两个重要参数影响其性能：1，初始容量，2，加载因子。容量是hash表中桶的数量。
 * 一般当Hashtable所含最大数目大于容量乘以加载因子，就会发生rehash。
 * hashtable是同步的，它使用synchronized对方法做同步处理。
 *
 * 由Collection接口的iterator方法返回的迭代器都是快速失败的，如果对iterator做修改，Iterator立马会抛出ConcurrentModificationException
 * 而hashTable的key和value返回的Enumeration不是快速失败的。
 * 其实现方法和hashMap类似，也是通过链表数组实现。默认初始化的初始容量是11。
 */
public class HashTableTest {

}
