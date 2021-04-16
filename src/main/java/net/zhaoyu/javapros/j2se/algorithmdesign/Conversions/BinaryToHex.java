package net.zhaoyu.javapros.j2se.algorithmdesign.Conversions;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @Description: 二进制转十六进制 ,每四位二进制转化为1位16进制。
 * @Author: zhaoyu
 * @Date: 2020/12/1
 */
public class BinaryToHex {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String binCopy;
        while (true) {
            System.out.println("Binary number: ");
            binCopy = in.next();
            if (binCopy .equals("0") ) {
                break;
            }
            System.out.println("十进制: "+convert2Hex(binCopy));
        }
        in.close();
    }

    private static String convert2Hex(String binary) {
        //构建16进制字符映射
        Map<Integer,String> hexMap=new HashMap<>();
        for (int i = 0; i < 10; i++) {
            hexMap.put(i, i+"");
        }
        for (int i = 0; i < 6; i++) {
            hexMap.put(i + 10, String.valueOf((char)('A' + i)));
        }

        int binBit,octalBit=0;
        String s="";

        //while和for实现分组处理。
        while (binary.length()>0) {
            //每4个一组转化为16进制。
            octalBit=0;
            for (int i = 0; i < 4; i++) {
                //字符串为空，结束循环
                if (binary.length() < 1) {
                    break;
                }
                binBit=Integer.parseInt(binary.substring(binary.length()-1));
                binary=binary.substring(0,binary.length()-1);
                //计算16进制位。
                octalBit+= binBit * (int) (Math.pow(2, i));
            }
            s= hexMap.get(octalBit)+s;
        }
        return s;
    }
}
