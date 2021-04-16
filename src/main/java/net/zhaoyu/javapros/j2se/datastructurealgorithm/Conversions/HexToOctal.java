package net.zhaoyu.javapros.j2se.datastructurealgorithm.Conversions;

import java.util.Scanner;

/**
 * @Description:
 * @Author: zhaoyu
 * @Date: 2020/12/2
 */
public class HexToOctal {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String deciml;
        while (true) {
            System.out.println("请输入16进制数: ");
            deciml = in.next();
            if (deciml.equals( "0")) {
                break;
            }
            System.out.println("8进制: "+convert2(deciml));
        }
        in.close();
    }

    public static String convert2(String numHex) {
        // 十六进制转换为整数。
        int conHex = Integer.parseInt(numHex, 16);
        String binary = Integer.toOctalString(conHex);
        return binary;
    }
}
