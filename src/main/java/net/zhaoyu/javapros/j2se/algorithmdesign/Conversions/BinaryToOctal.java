package net.zhaoyu.javapros.j2se.algorithmdesign.Conversions;

import java.util.Scanner;

/**
 * @Description: 二进制转8进制 ,每三位二进制转化为1位8进制。如：11010101=325。
 * @Author: zhaoyu
 * @Date: 2020/11/30
 */
public class BinaryToOctal {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int binCopy;
        while (true) {
            System.out.println("Binary number: ");
            binCopy = in.nextInt();
            if (binCopy == 0) {
                break;
            }
            System.out.println("十进制: "+convert2Octal(binCopy));
        }
        in.close();
    }

    private static String convert2Octal(int binary) {
        int binBit,octalBit=0;
        String s="";
        //while和for实现分组处理。
        while (binary > 0) {
            //每3个一组转化为8进制。
            octalBit=0;
            for (int i = 0; i < 3; i++) {
                binBit=binary%10;
                binary=binary/10;
                //计算八进制位。
                octalBit+= binBit * (int) (Math.pow(2, i));
            }
            s= octalBit+s;
        }
        return s;
    }
}
