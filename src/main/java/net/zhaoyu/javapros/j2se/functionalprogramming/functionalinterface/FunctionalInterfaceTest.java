package net.zhaoyu.javapros.j2se.functionalprogramming.functionalinterface;

/**
 * 一个functional interface 是拥有只有一个非Object的未实现的方法的接口。实现该接口的类只实现一个方法。
 * 如果有多个非Object的方法，则方法必须有default实现。
 */
@FunctionalInterface
public interface FunctionalInterfaceTest {
    int test(int y);
    //FunctionalInterface 的其他接口必须是default。
    default  int test2(int y){
        return test(2);
    };
}


