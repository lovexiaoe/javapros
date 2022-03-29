package net.zhaoyu.javapros.j2se.basic.oop.execution;

/**
 * 描述接口初始化的过程。
 *
 * 参考java-language-specification java-执行部分。
 *
 * 一个类或接口T在发生下列情况时，会立即初始化：
 *  1. 一个类的实例被创建。
 *  1. 静态方法被调用。
 *  1. 静态字段被赋值。
 *  1. 静态字段被使用且字段不是常量。
 *  1. T是一个顶级类，T内嵌套的assert语句被执行。
 *
 * 输出结果：
 * 1
 * j=3
 * jj=4
 * 3
 *
 * 对J.i的引用是一个常量调用，不会引起I被初始化。
 * 对K.j的引用非常量调用，这就引起接口J中字段的初始化，但不会引起父接口I中的初始化，也不会引起K中的字段初始化。
 */
public class InterfaceInitTest {
    public static void main(String[] args) {
        System.out.println(J.i);  // 1
        System.out.println(K.j);  // 初始化 j=3,jj=4 ,K.j的值为3.
    }
    static int out(String s, int i) {
        System.out.println(s + "=" + i);
        return i;
    }
}
interface I {
    int i = 1; //常量
    int ii = InterfaceInitTest.out("ii", 2);
}
interface J extends I {
    int j = InterfaceInitTest.out("j", 3);
    int jj = InterfaceInitTest.out("jj", 4);
}
interface K extends J {
    int k = InterfaceInitTest.out("k", 5);
}