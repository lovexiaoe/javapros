package net.zhaoyu.javapros.j2se.basic.oop.execution;

/**
 * 该例子说明父类-子类的加载成员的顺序。
 *
 * 父类静态成员(静态变量，静态块，静态方法)—>子类静态成员—>父类的一般成员—>父类的构造方法
 * -->子类的一般成员—>子类的构造方法。
 *
 * 参考java-language-specification java-执行部分。
 */
public class LoadOrderTest {
    public static void main(String[] args) {
        A ab=new B();  //打印出1 a 2 b，和ab为A类型没有关系。
        ab=new B();   //打印出2b。
    }
}
class A{
    static {
        System.out.println("1");
    }

    public A() {
        System.out.println("2");
    }
}
class B extends A{
    static {
        System.out.println('a');
    }

    public B(){
        System.out.println("b");
    }
}
