package net.zhaoyu.javapros.j2se.functionalprogramming.functionalinterface;

public class Main {
    public static void main(String[] args) {
        //FunctionInterface的lambda调用。可以使用lambda定义其实现。
        FunctionalInterfaceTest fit=(x)->x+1;
        System.out.println(fit.test(3));//4
    }
}
