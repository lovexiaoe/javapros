package com.zhaoyu.collections;

/**
 * 在java类库中，集合类的基本接口都是Collection。
 * Collection有两个基本方法
 * public interface Collection<E>
 * {
 * boolean add(E element)
 * Iterator<E> iterator();
 * }
 *
 * iterator方法返回一个实现了Iterator接口的对象，Iterator有以下三个方法：
 * public interface Iterator{
 * E next();
 * boolean hasNext();
 * void remove();
 * }
 *
 * Iterable接口返回一个Iterator对象，它只有一个方法。
 * public interface Iterable<E>{
 * Iterator<E> iterator();
 * }
 * foreach循环可以操作任何实现Iterable接口的对象。
 *
 * @author xiaoE
 *
 */

/**
 * Collection接口的方法众多，如果想构造一个实现这个接口的工具类，那么将是一件烦人的事情 ，
 * java类库提供了一个类AbstractCollection,它将基本的size和iterator方法抽象化了，但是提供了其它例行方法。
 *
 */
public class CollectionTest {

}
