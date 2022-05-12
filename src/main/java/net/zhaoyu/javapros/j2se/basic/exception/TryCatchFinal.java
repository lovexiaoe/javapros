package net.zhaoyu.javapros.j2se.basic.exception;

/**
 * 在try-catch-finally中，如果try-catch模块和finally模块都有异常或者return发生，那么finally中的异常或return会覆盖try-catch模块中的
 * 异常或者return。
 * @Author: zhaoyu
 * @Date: 2020/10/20
 */
public class TryCatchFinal {
    public static void main(String[] args) {
        System.out.println(tryCatchFinallyReturn());
        System.out.println("===============================");
        tryCatchFinallyThrow();
    }

    private static void tryCatchFinallyThrow() {
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

    private static String tryCatchFinallyReturn() {
        try {
            System.out.println("try block:");
            return  "try block return";
        } catch (Exception e) {
            System.out.println("catch block");
            return  "catch block return";
        }finally {
            System.out.println("finally block:");
            return  "finally block return";
        }
    }
}
