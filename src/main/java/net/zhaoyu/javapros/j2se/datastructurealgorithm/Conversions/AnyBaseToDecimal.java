package net.zhaoyu.javapros.j2se.datastructurealgorithm.Conversions;

/**
 * @Description: 任何进制转换为十进制，超过10进制的使用ASCII字符从A开始表示。
 * @Author: zhaoyu
 * @Date: 2020/12/16
 */
public class AnyBaseToDecimal {

    public static void main(String[] args) {
        System.out.println( convert("1010", 2) == Integer.valueOf("1010", 2));
        System.out.println( convert("777", 8) == Integer.valueOf("777", 8));
        System.out.println( convert("999", 10) == Integer.valueOf("999", 10));
        System.out.println( convert("ABCDEF", 16) == Integer.valueOf("ABCDEF", 16));
        System.out.println( convert("XYZ", 36) == Integer.valueOf("XYZ", 36));
    }


    public static int convert(String source,int radix){
        int m=1 ,s=0;
        for (int i = source.length()-1; i >=0; i--) {
            char c = source.charAt(i);
            int digit=charInt(c);

            //判断输入的字符超过进制限制的异常
            if (digit >= radix) {
                throw new NumberFormatException("错误的字符" + c);
            }

            s+=digit*m;
            m*=radix;
        }
        return s;
    }

    //返回字符对于的10进制数字。
    public static int charInt(char c) {
        //非大写字母或非数字判断
        if (!(Character.isUpperCase(c) || Character.isDigit(c))) {
            throw new NumberFormatException("非法的字符："+c);
        }
        return Character.isDigit(c)? c-'0' : c - 'A' + 10;
    }
}
