package net.zhaoyu.javapros.j2se.basic.math;

/**
 * int，double等数学计算超过他们的最大值，结果就会变得不可预期
 */

public class IntegerTest {
    public static void main(String[] args) {
        int muti_max=Integer.MAX_VALUE*17;
        System.out.println(Integer.MAX_VALUE*17>Integer.MAX_VALUE ); //false
        System.out.println(Integer.MAX_VALUE);
        System.out.println(muti_max);
    }
}
