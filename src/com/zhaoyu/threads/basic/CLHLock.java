package com.zhaoyu.threads.basic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * CLH队列锁是一种FIFO队列的自旋锁，能确保无饥饿性，提供先来先服务的公平性，CLH是不可重入锁。
 * 1.初始状态 tail指向一个node(head)节点
 * +------+
 * | head | <---- tail
 * +------+
 *
 * 2.lock-thread加入等待队列: tail指向新的Node，同时Prev指向tail之前指向的节点
 * +----------+
 * | Thread-A |
 * | := Node  | <---- tail
 * | := Prev  | -----> +------+
 * +----------+        | head |
 *                     +------+
 *
 *             +----------+            +----------+
 *             | Thread-B |            | Thread-A |
 * tail ---->  | := Node  |     -->    | := Node  |
 *             | := Prev  | ------|    | := Prev  | ----->  +------+
 *             +----------+            +----------+         | head |
 *                                                          +------+
 * 3.寻找当前node的prev-node然后开始自旋
 *
 */

public class CLHLock implements Lock{
    private final AtomicReference<Node> tail=new AtomicReference<Node>(new Node());
    private final ThreadLocal<Node> pred;
    private final ThreadLocal<Node> node;

    public CLHLock() {
        this.pred = new ThreadLocal<Node>(){
            protected  Node initialValue(){
                return new Node();
            }
        };
        this.node = new ThreadLocal<Node>(){
            protected  Node initialValue(){
                return new Node();
            }
        };
    }

    //入队列操作，在tail前添加节点，设置status为true。
    @Override
    public void lock() {
        final Node node=this.node.get();
        node.locked=true;
        Node pred=this.tail.getAndSet(node);  //getAndSet 设置新值node，返回原来的就值，这里产生竞争条件，所以使用同步。
        this.pred.set(pred);
        while(pred.locked);                   //自旋，如果pred释放锁，则跳出自旋获得锁。
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    //出队列操作，当前的node设置为pred,则后面的Node会指向pred,设置status为false。
    @Override
    public void unlock() {
        final Node node=this.node.get();
        node.locked=false;
        this.node.set(this.pred.get());
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    private static class Node{
        private volatile boolean locked;
    }
}