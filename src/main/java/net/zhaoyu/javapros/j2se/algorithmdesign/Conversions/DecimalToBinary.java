package net.zhaoyu.javapros.j2se.algorithmdesign.Conversions;

import java.util.Scanner;

/**
 * @Description: 10进制转二进制
 * @Author: zhaoyu
 * @Date: 2020/12/1
 */
public class DecimalToBinary {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int deciml;
        while (true) {
            System.out.println("请输入10进制数: ");
            deciml = in.nextInt();
            if (deciml == 0) {
                break;
            }
            System.out.println("二进制: "+convert(deciml));
        }
        in.close();
    }

    private static String convert(int deciml) {
        int bit,power=1;
        String s="";
        while (deciml > 0) {
            //有余数，位数为1，否则为0
            bit = deciml%2;
            deciml=deciml/2;
            s=bit+s;
        }
        return s;
    }
}
