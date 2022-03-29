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
 * 执行结果 1729，没有打印出Sub。Sub.taxi被调用，实际上只是引用Super中声明。并没有触发Sub的初始化。
 */
public class StaticFieldInit {
    public static void main(String[] args) {
        System.out.println(Sub.taxi);
    }
}

class Super1 {
    static int taxi = 1729;
}

class Sub extends Super1 {
    static { System.out.print("Sub ");}
}

