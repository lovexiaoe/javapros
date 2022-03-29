package net.zhaoyu.javapros.j2se.basic.oop.execution;

/**
 * 参考java-language-specification java-执行部分。
 *
 * 一个类或接口T在发生下列情况时，会立即初始化：
 * 1. 一个类的实例被创建。
 * 1. 静态方法被调用。
 * 1. 静态字段被赋值。
 * 1. 静态字段被使用且字段不是常量。
 * 1. T是一个顶级类，T内嵌套的assert语句被执行。
 *
 * 执行结果 Super Two
 */
public class StaticInit {
    public static void main(String[] args) {
        One o = null; //没有实例化，One不会初始化，
        Two t = new Two(); //Two实例化时，先执行初始化，发现有父类，优先初始化父类。
    }
}
class Super {
    static { System.out.print("Super "); }
}
class One {
    static { System.out.print("One "); }
}
class Two extends Super {
    static { System.out.print("Two "); }
}
