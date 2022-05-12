package net.zhaoyu.javapros.j2se.threads;

/**
 * final的并发语义:
 * final定义只会初始化一次，它的语义和正常的字段有所不同。final的读取可以很自由地穿越同步屏障，然后调用到任意方法。
 * 非final字段需要重新加载，而final字段可以缓存到寄存器，不需要从内存中重新加载。final字段可以让程序不使用synchronization实现线程安全的
 * 不可变对象。
 * 一个对象在构造方法执行完成后，会确保final字段被初始化，线程只能看到final字段完全初始化后的对象引用。所以使用final字段原则很简单：
 * 1. 在构造方法中设置final字段。
 * 2. 在构造方法完成之前不要让另一个线程看到正在初始化过程中的对象。
 *
 * final字段会在构造方法完成退出前完成赋值。如果在构造方法执行期间读取了final字段，可能会读取到初始值。
 */
public class FinalTest {
    final int x;
    int y;

    static FinalTest f;

    public FinalTest() {
        x=3;
        y = 4;
    }

    static void writer(){
        f = new FinalTest();
    }

    static void reader(){
        if (f != null) {
            int i=f.x; //使用了final，构造方法在读取变量之前保证final字段初始化;
            int j=f.y; //非final字段可能还没有被初始化完成，j就得到了f的引用地址，所以可能会看到0。
        }
    }
}

