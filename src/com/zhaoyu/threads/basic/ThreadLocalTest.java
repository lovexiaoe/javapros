package com.zhaoyu.threads.basic;

import java.lang.ThreadLocal;
/**
 * ThreadLocal为每个线程提供一个变量的副本。如CLH队列锁中的node变量使用ThreadLocal，为每个线程创建一个节点。Servlet为每个
 * request请求下的一些变量存储。Spring单例需要为每个线程设定不同的变量（dbName）都可以使用ThreadLocal。
 * ThreadLocal内部是一个Map结构，ThreadLocalMap，它的Entry使用WeakReference实现。Thread类存放了一个ThreadLocal变量。
 */


public class ThreadLocalTest {

}
