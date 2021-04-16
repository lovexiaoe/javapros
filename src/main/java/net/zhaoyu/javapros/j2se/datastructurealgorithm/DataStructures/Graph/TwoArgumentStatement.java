package net.zhaoyu.javapros.j2se.datastructurealgorithm.DataStructures.Graph;

/**
 * 定义一个两个参数的功能接口。用于lambda表达式。
 * @param <E>
 * @param <F>
 */
@FunctionalInterface
public interface TwoArgumentStatement <E, F>{
    void doSomething(E e,F f);
}
