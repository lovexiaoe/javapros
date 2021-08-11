package net.zhaoyu.javapros.test;


import java.util.Scanner;

//用于自己临时调试一些程序。
public class Test {
    public static void main(String[] args) {
        statistic();
    }

    static void statistic(){
        Scanner stdin = new Scanner(System.in);
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            System.out.println("请输入性别m或者f，男：m，女：f");
            String sex = stdin.next();
            System.out.println("请输入年龄：");
            final int age = stdin.nextInt();
            if (sex.equals("f") && age < 23 && age > 19) {
                sum++;
            }
        }
        System.out.println("符合条件的足球女运动员有"+sum+"个。");
    }
}

