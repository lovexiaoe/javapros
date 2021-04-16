package net.zhaoyu.javapros.j2se.datastructurealgorithm.Conversions;

import java.util.Scanner;

/**
 * @Description: 十进制转八进制
 * @Author: zhaoyu
 * @Date: 2020/12/1
 */
public class DecimalToOctal {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int deciml;
        while (true) {
            System.out.println("请输入10进制数: ");
            deciml = in.nextInt();
            if (deciml == 0) {
                break;
            }
            System.out.println("八进制: "+convert(deciml));
        }
        in.close();
    }

    private static String convert(int deciml) {

        String s="";
        while (deciml > 0) {
            //取余数
            int bit = deciml %8;
            s=bit+s;
            deciml=deciml/8;
        }
        return s;
    }
}
