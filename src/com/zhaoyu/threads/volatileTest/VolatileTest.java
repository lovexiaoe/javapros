package com.zhaoyu.threads.volatileTest;

/**
 * volatile修饰的成员变量在每次被线程访问时，都强迫从主内存中重读该成员变量的值。而且，当成员变量发生变化时，
 * 强迫线程将变化值回写到主内存。这样在任何时刻，两个不同的线程总是看到某个成员变量的同一个值。
 *
 * volatile提供了两种功能：1，禁用线程对成员变量的私有拷贝，改变对所有县城可见。2，禁用VM对程序的重排序优化。
 *
 * 使用场景，在共享的成员变量上使用，synchronized代码块或者常量，不必使用。
 */
public class VolatileTest {
    private volatile boolean done;
    public boolean isDone(){return done;}
    public void setDone(){done=true;}

    //volatile提供了线程之间的可见性，但是并没有提供原子性。所以在多线程场景下使用，需要保证访问资源的原子性。
    // 如下，done=!done有一读一写的操作，会出现并发问题。
    public void flipDone(){done=!done;}
}
