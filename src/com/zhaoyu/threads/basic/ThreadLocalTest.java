package com.zhaoyu.threads.basic;

/**
 * ThreadLocal为每个线程提供一个变量的副本。如CLH队列锁中的node变量使用ThreadLocal，为每个线程创建一个节点。Servlet为每个
 * request请求下的一些变量存储。Spring单例需要为每个线程设定不同的变量（dbName）都可以使用ThreadLocal。
 */


public class ThreadLocalTest {

}
