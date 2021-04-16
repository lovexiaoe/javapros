package net.zhaoyu.javapros.j2se.algorithmdesign.Conversions;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @Description: 10进制转16进制
 * @Author: zhaoyu
 * @Date: 2020/12/1
 */
public class DecimalToHex {
    //构建16进制字符映射
    static Map<Integer,String> hexMap=new HashMap<>();
    static{
        for (int i = 0; i < 10; i++) {
            hexMap.put(i, i+"");
        }
        for (int i = 0; i < 6; i++) {
            hexMap.put(i + 10, String.valueOf((char)('A' + i)));
        }
        System.out.println(hexMap.toString());
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int deciml;
        while (true) {
            System.out.println("请输入10进制数: ");
            deciml = in.nextInt();
            if (deciml == 0) {
                break;
            }
            System.out.println("16进制: "+convert(deciml));
        }
        in.close();
    }

    private static String convert(int deciml) {

        String s="";
        while (deciml > 0) {
            //取余数
            int hex = deciml %16;
            s= hexMap.get(hex) +s;
            deciml=deciml/16;
        }
        return s;
    }
}
