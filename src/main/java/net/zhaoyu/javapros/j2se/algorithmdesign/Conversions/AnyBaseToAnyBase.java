package net.zhaoyu.javapros.j2se.algorithmdesign.Conversions;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @Description: 任意进制的转换,进制需要在2-36之间。
 * @Author: zhaoyu
 * @Date: 2020/12/16
 */
public class AnyBaseToAnyBase {

    //规定最小Base
    static final int MINIMUM_BASE = 2;
    //规定最大Base
    static final int MAXIMUM_BASE = 36;

    public static void main(String[] args) {

        System.out.println( convert("1010", 2,20));
        System.out.println( convert("XYZ", 36,20));
        System.out.println( convert("157", 8,16));
        System.out.println( convert("6F", 16,8));
    }

    //验证输入正确性。
    public static boolean validateForBase(String s,int base){
        char[] digitChars = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
                'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
        };
        // 根据base复制
        char[] digitsForBase = Arrays.copyOfRange(digitChars, 0, base);

        // 转换为set
        HashSet<Character> digitsSet = new HashSet<>();
        for (int i = 0; i < digitsForBase.length; i++) digitsSet.add(digitsForBase[i]);

        for (char c : s.toCharArray()) {
            if (!digitsSet.contains(c)) {
                return false;
            }
        }
        return  true;
    }

    public static String convert(String source,int sBase,int tBase){
        return decimalToAnyBase(anyBaseToDecimal(source, sBase), tBase);
    }


    public static int anyBaseToDecimal(String source, int radix) {
        int m = 1, s = 0;
        for (int i = source.length() - 1; i >= 0; i--) {
            char c = source.charAt(i);
            int digit = c>='A'?10+c-'A':c-'0';

            //判断输入的字符超过进制限制的异常
            if (digit >= radix) {
                throw new NumberFormatException("错误的字符" + c);
            }

            s += digit * m;
            m *= radix;
        }
        return s;
    }

    public static String decimalToAnyBase(int decimal, int tBase) {
        System.out.println("十进制数："+decimal);
        String s = "";
        int remainder;
        while (decimal > 0) {
            remainder=decimal % tBase;
            s=(char)(remainder>=10?('A'+remainder-10):remainder+'0')+s;
            decimal/=tBase;
        }
        return s;
    }
}
