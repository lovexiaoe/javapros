package net.zhaoyu.javapros.j2se.algorithmdesign.Conversions;

import java.util.Scanner;

/**
 * @Description: 二进制转其他进制
 * @Author: zhaoyu
 * @Date: 2020/12/1
 */
public class DecimalToAnyBase {
    //获取十进制转换成对应进制的字符
    private static char getBaseValue(int index){
        if (index < 10) {
            return (char) (index+'0');
        } else {
            return (char) ('A' + index - 10);
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("请输入10进制数: ");
            int deciml = in.nextInt();
            if (deciml == 0) {
                break;
            }
            System.out.println("请输入转换结果进制数: ");
            int base = in.nextInt();
            System.out.println("二进制: "+convert(deciml,base));
        }
        in.close();
    }

    private static String convert(int deciml,int base) {
        int bit;
        String s="";
        while (deciml > 0) {
            bit = deciml%base;
            deciml=deciml/base;
            s=getBaseValue(bit)+s;
        }
        return s;
    }


}
