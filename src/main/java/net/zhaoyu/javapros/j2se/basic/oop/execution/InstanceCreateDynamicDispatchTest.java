package net.zhaoyu.javapros.j2se.basic.oop.execution;

/**
 * 输出结果：
 * 0
 * 3
 *
 * 该例子说明父类Super2中的构造方法不是调用Super2中的printThree方法，而是调用了子类中重写的printThree方法。
 * 这个方法在子类Test字段初始化之前已经被执行了，所以第一次打印出了0。
 * 第二次执行子类变量初始化完成后，输出3。
 */
public class InstanceCreateDynamicDispatchTest extends Super2 {
    int three = ((int) Math.PI);
    @Override
    void printThree() {
        System.out.println(three);
    }

    public static void main(String[] args) {
        InstanceCreateDynamicDispatchTest test = new InstanceCreateDynamicDispatchTest();
        test.printThree();
    }
}

class Super2{
    public Super2() {
        printThree(); //构造方法调用到这里时，printThree调用了子类中重写的方法，重写方法中实例变量还没有被初始化。只给了默认值0。
    }
    void printThree(){System.out.println("three");}
}
