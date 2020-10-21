package net.zhaoyu.javapros.j2se.basic.exception;

/**
 * @Description:  在try-catch-finally中，如果try模块和finally模块都有异常抛出，那么finally中的异常会优先覆盖try代码块中的。
 * @Author: zhaoyu
 * @Date: 2020/10/20
 */
public class TryCatchFinal {
    public static void main(String[] args) {
        try {
            System.out.println("try block:");
            throw new RuntimeException("try block exception");
        } catch (Exception e) {
            System.out.println("catch block");
            throw new RuntimeException("catch block Exception");
        }finally {
            System.out.println("finally block:");
            throw new RuntimeException("finally block exception");
        }
    }
}
