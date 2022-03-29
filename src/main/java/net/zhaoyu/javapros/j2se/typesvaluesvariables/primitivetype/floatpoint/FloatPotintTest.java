package net.zhaoyu.javapros.j2se.typesvaluesvariables.primitivetype.floatpoint;

/**
 * 计算机由于采用二进制保存数据，是无法精确保存一些小数的，比如1/10,所以使用float和double进行计算的时候会出现超预期的结果，
 * 如0.1*3得到的可能是0.30000000000004。所以浮点数之间的等值判断，不能用==或者equals来比较，尽量使用BigDecimal。
 *
 * float和double类型，概念上与IEEE 754定义的32位单精度和64位双精度格式的值和操作相关联。
 *
 * IEEE 754包括了由符号和大小组成的正数或者负数。也包括了+0和-0。正负无穷大。
 * 还有一个特殊的NaN。NaN表示某种非法的操作结果。如0/0。 NaN被定义为Float.NaN和Double.NaN。
 *
 * 每种java程序的实现都需要提供两种标准的浮点数值集合。被称为float value set和double value set。
 * 还需提供扩展指数浮点集，被称为 float-extend-exponent value set 和double-extend-exponent value set。
 * 这些扩展指数集在某些情况下可以被用来表示float和double。
 *
 * 有限的非0 浮点数都可以被表示为：s.m.2(e-N+1), s为-1或者1。m为小于2的N次方的正整数。e是在
 *  E(min) = -(2的K-1次方-2)
 *  E(max) = 2的K-1次方-1
 * 两个数之间的整数。N和K是由值集合。
 *
 * 如果一个值可以被表达为上述格式，且m>=2的N-1次方。那么被称为正常的。否则，这种表达被称为不正常的。这个值被称为非正常值。
 *
 * 正0和负0是相等的，即-0.0==0.0返回true。但是其他操作区分正负0。如1.0/0.0是正无穷大，1.0/-0.0是负无穷大。
 *
 * 如果运算使用了32位的浮点数计算，则操作结果是float。其中一个操作数不是float，会先转换为float进行运算。
 *
 * java 会将浮点数操作的结果四舍五入，得到无限接近真正结果的值。
 *
 * float运算会因为unboxing转换的值为null产生NullPointException，在++和--操作时boxing所需的内存不足产生OutOfMemoryError。
 */
public class FloatPotintTest {
    public static void main(String[] args) {

        //Infinity。float操作在上溢时会得到一个有符号的Infinity，不会自动进行double转换。
        double d = 1e308;
        System.out.print("计算上溢得到 infinity: ");
        System.out.println(d + "*10==" + d*10);

        //当指数太低，无法表示一个值时，会发生下溢，结果为0.0。
        d = 1e-305 * Math.PI;
        System.out.print("逐步下溢: " + d + "\n ");
        for (int i = 0; i < 4; i++)
            System.out.print(" " + (d /= 100000));  //第4个数指数精度太小，无法表示。
        System.out.println();

        //  NaN操作：一个数和NaN操作得到的结果都是NaN
        System.out.print("0.0/0.0 是 ");
        d = 0.0/0.0;
        System.out.println(5.5d+d);

        // float的不精确结果和四舍五入:
        System.out.print("0-100的非精确 float值:");
        for (int i = 0; i < 100; i++) {
            float z = 1.0f / i;
            if (z * i != 1.0f)
                System.out.print(" " + i);
        }
        System.out.println();

        // Double的不精确结果和四舍五入。
        System.out.print("0-100的非精确 double值:");
        for (int i = 0; i < 100; i++) {
            double z = 1.0 / i;
            if (z * i != 1.0)
                System.out.print(" " + i);
        }
        System.out.println();

        // 转换为整数时的截取
        System.out.print("浮点数转化为整数: ");
        d = 12345.6;
        System.out.println((int)d + " " + (int)(-d));
    }
}
