package net.zhaoyu.javapros.j2se.basic;

/**
 * final用于修饰以个常量，编译器在编译时，就会将变量替换为常量，也可以修饰参数。
 */
public class FinalTest {
    //用作变量
    public static final int MAX=200;
    //用作方法参数。
    void someMethod(final int[] a)
    {
        //a=new int[2];  //禁止改变final修饰的参数。
        a[0]=7;        //可以修改final的内部状态。
    }
    //修饰方法，禁止方法重写
    final void test(){

    }
    //修饰类，禁止该类被继承
    final class NotExtent{

    }
}
