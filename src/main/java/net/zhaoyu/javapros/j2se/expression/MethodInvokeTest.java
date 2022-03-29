package net.zhaoyu.javapros.j2se.expression;

/**
 * 在编译时，解析方法的名称由于方法重载的原因，会比解析字段复杂。在运行时，因为方法的覆盖，调用一个方法也比访问一个字段复杂。
 *
 * 下面展示了继承调用父类方法的写法和实现父接口，调用父接口的方法。
 */
public class MethodInvokeTest {
    public static void main(String[] args) throws InterruptedException {
        Subclass1 sub = new Subclass1();
        sub.tweak();
        Subclass2 sub2=new Subclass2();
        sub2.tweak();
    }

}
class Superclass {
    void foo() { System.out.println("Hi"); }
}
class Subclass1 extends Superclass {
    void foo() { throw new UnsupportedOperationException(); }
    void tweak () {
        Subclass1.super.foo(); // Gets the 'println' behavior
    };
}

interface Superinterface {
    default void foo() { System.out.println("Hi"); }
}
class Subclass2 implements Superinterface {
    public void foo() { throw new UnsupportedOperationException(); }
    void tweak() {
        Superinterface.super.foo(); //实例调用父接口方法时，命名为父接口
    }
}