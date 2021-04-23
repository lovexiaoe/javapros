package net.zhaoyu.javapros.test;


//用于自己临时调试一些程序。
public class Test {
    public static void main(String[] args) {
        int cap=13;
        int n = cap - 1;
        n |= n >>> 1;
        System.out.println("1="+n);
        n |= n >>> 2;
        System.out.println("2="+n);
        n |= n >>> 4;
        System.out.println("4="+n);
        n |= n >>> 8;
        System.out.println("8="+n);
        n |= n >>> 16;
        System.out.println("16="+n);
    }
}

