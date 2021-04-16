package net.zhaoyu.javapros.j2se.algorithmdesign.Conversions;

import java.util.Scanner;

/**
 * @Description: 二进制转十进制
 * @Author: zhaoyu
 * @Date: 2020/11/30
 */
public class BinaryToDecimal {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int binCopy,d,s=0,power=0;
        System.out.println("Binary number: ");
        binCopy=in.nextInt();
        while (binCopy != 0) {
            //取末尾数字
            d=binCopy%10;
            //转换末尾数字，并累加
            s += d * (int) (Math.pow(2, power++));
            //截取末尾数字
            binCopy /=10;
        }
        System.out.println("Decimal equivalent:"+s);
        in.close();
    }
}
