package net.zhaoyu.javapros.j2se.typesvaluesvariables.primitivetype.integral;

/**
 * 整数类型包括了，
 * byte, 8位-128到127
 * short, 16位-32768 to 32767
 * int, 32位-2147483648 to 2147483647
 * long 64位-9223372036854775808 to 9223372036854775807
 * char, 从\u0000到\uffff 。即从0到65535。
 *
 * 整数类型的操作不做任何溢出处理。
 *
 * 整数类型会抛出如下的异常：
 * 1 unboxing转换一个null引用会抛出NullPointException。
 * 2 /或者%操作会有除数为0的情况。会抛出ArithmeticException。
 * 3 ++或者--在boxing转换的时候会爆出OutOfMemoryError，可能没有足够的内存执行转换。
 */
public class IntegralTest {
    public static void main(String[] args) {
        int i=1000000;
        System.out.println(i * i); //-727379968,int溢出。
        long l=i;
        System.out.println(l * l); //1000000000000 long长度足够
        System.out.println(20296/(l-i)); // ArithmeticException异常，l-i会包i转换为long再相减，所以除数为0
    }


}
