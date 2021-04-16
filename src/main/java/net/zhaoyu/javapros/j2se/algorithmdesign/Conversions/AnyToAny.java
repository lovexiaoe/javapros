package net.zhaoyu.javapros.j2se.algorithmdesign.Conversions;

/**
 * @Description: 给出一个数字和其进制数，目标进制数，返回目标数字。 没有处理十进制以上，所以，只能处理小于等于10进制的数字。
 * @Author: zhaoyu
 * @Date: 2020/12/9
 */
public class AnyToAny {
    public static void main(String[] args) {
        System.out.println("2进制1001，转换为10进制，结果为："+convert(1001,2,10));
        System.out.println("8进制157，转换为16进制，结果为："+convert(157,8,16));
    }
    static Integer convert(int source,int sourceBase,int targetBase){
        boolean b;
        int decimal=0,s=0,m=1,dn=0;
        //原进制转换为10进制
        while(source>0){
            decimal+=(source%10)*m;
            m*=sourceBase;
            source/=10;
        }
        //由10进制转换为目标进制
        m=1;
        while (decimal > 0) {
            dn+=(decimal%targetBase)*m;
            m*=10;
            decimal/=targetBase;
        }
        return dn;
    }
}
