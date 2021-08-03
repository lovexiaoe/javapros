package net.zhaoyu.javapros.j2se.threads.basic;

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
    //前节点
    private final ThreadLocal<Node> pred;
    //当前节点
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

    //获取锁
    @Override
    public void lock() {
        final Node node=this.node.get();
        node.locked=true; //当前节点设置为锁定状态。
        Node pred=this.tail.getAndSet(node);  //tail指向当前节点，返回原来的旧值，这里产生竞争条件，所以使用同步。
        this.pred.set(pred);                  //前节点设置为上一个获取锁的节点。
        while(pred.locked);                   //如果前节点为锁定状态，进入自旋，如果pred释放锁，则跳出自旋获得锁。
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

    //释放锁
    @Override
    public void unlock() {
        final Node node=this.node.get();
        node.locked=false; //当前节点取消锁定状态。
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
