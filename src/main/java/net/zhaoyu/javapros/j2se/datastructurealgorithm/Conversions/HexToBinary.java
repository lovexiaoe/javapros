package net.zhaoyu.javapros.j2se.datastructurealgorithm.Conversions;

import java.util.Scanner;

/**
 * @Description: 十六进制转二进制
 * @Author: zhaoyu
 * @Date: 2020/12/2
 */
public class HexToBinary {
    private static int getDecimalFromHex(char hex){
        if (hex < '0' + 10 && hex>'0') {
            return hex - '0';
        } else {
            return hex -'A'+10;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String deciml;
        while (true) {
            System.out.println("请输入16进制数: ");
            deciml = in.next();
            if (deciml.equals( "0")) {
                break;
            }
            System.out.println("2进制: "+convert(deciml));
            System.out.println("2进制: "+convert2(deciml));
        }
        in.close();
    }

    private static String convert(String hex) {

        String s="";
        char c;
        while (hex.length()>0) {
            c = hex.charAt(hex.length()-1);
            hex = hex.substring(0, hex.length() - 1);
            int decimal = getDecimalFromHex(c);
            for (int i = 0; i < 4; i++) {
                int remainder = decimal % 2;
                decimal=decimal/2;
                s=remainder+s;
            }
        }
        return s;
    }

    //第二种转换方式
    public static String convert2(String numHex) {
        // 十六进制转换为整数。
        int conHex = Integer.parseInt(numHex, 16);
        // 整数转换为二进制
        String binary = Integer.toBinaryString(conHex);
        return binary;
    }
}
