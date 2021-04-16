package net.zhaoyu.javapros.j2se.algorithmdesign.Conversions;

import java.util.Scanner;

/**
 * @Description:
 * @Author: zhaoyu
 * @Date: 2020/12/2
 */
public class HexToDecimal {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String deciml;
        while (true) {
            System.out.println("请输入16进制数: ");
            deciml = in.next();
            if (deciml.equals( "0")) {
                break;
            }
            System.out.println("10进制: "+convert2(deciml));
        }
        in.close();
    }

    public static int convert2(String hex) {
        String hexString = "0123456789ABCDEF";
        String s;
        hex = hex.toUpperCase();
        int ret = 0,bitDecimal,power=0;
        for (int i = 0; i < hex.length(); i++) {
            bitDecimal = hexString.indexOf(hex.charAt(i));
            ret=16*ret+bitDecimal;
        }

        return  ret;
    }
}
