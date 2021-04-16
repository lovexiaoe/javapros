package net.zhaoyu.javapros.j2se.basic.math;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * BigDecimal由一个任意精度的BigInteger值，和一个32bit的Integer的次方数组成。
 *
 * BigDecimal 指定一个舍入算法。如果没有指定，就不能正确表示并抛出一个异常。
 *
 * 主要的属性为intVal：没有进行幂运算的数,BigInteger。
 * scale:int类型的次方值。precise:int类型的总数字长度，如1.23，scale=2,precise=3
 */
public class BigDecimalTest {
    // 默认除法运算精度
    private static final Integer DEF_DIV_SCALE = 2;

    /**
     * 提供精确的加法运算。
     *
     */
    public static Double add(Double value1, Double value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1));
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        return b1.add(b2).doubleValue();
    }


    public static Double sub(Double value1, Double value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1));
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        return b1.subtract(b2).doubleValue();
    }

    public static Double mul(Double value1, Double value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1));
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        return b1.multiply(b2).doubleValue();
    }

    //除法，指定小数长度和取舍方法。
    public static Double divide(Double dividend, Double divisor, Integer scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(dividend));
        BigDecimal b2 = new BigDecimal(Double.toString(divisor));
        return b1.divide(b2, scale, RoundingMode.HALF_UP).doubleValue();
    }

    //四舍五入
    public static double round(double value, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(value));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, RoundingMode.HALF_UP).doubleValue();
    }

    public static void main(String[] args) {
        System.out.println(mul(0.12332,0.23234));
        System.out.println(divide(23.12332,0.13234,10));
        MathContext mc=MathContext.DECIMAL32; //精度为7
        BigDecimal bd=new BigDecimal("0.1232322223",mc);
        System.out.println(bd);                           //0.1232322

        //禁止使用BigDecimal(double)方法创建对象，存在精度丢失风险。
        BigDecimal bd2 = new BigDecimal(0.1f); //实际的存储值可能为0.10000000149

        //设置精度
        BigDecimal m = new BigDecimal("1.255433");
        System.out.println( m.setScale(3,BigDecimal.ROUND_HALF_DOWN));// 1.255

        //指定精度计算
        BigDecimal one = new BigDecimal("1");
        System.out.println(bd.divide(one, 5, RoundingMode.HALF_UP).doubleValue()); //0.12323
    }
}
