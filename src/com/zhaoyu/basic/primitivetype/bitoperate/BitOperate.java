package com.zhaoyu.basic.primitivetype.bitoperate;

/**
 * 左移时，符号位不变，低位补0
 * 右移时，正数高位补0，负数在右移时，是以补码的形式操作的，移动时高位补符号位。移动完后-1取反。
 * 无符号位右移时，高位都补0，负数在无符号右移中会变成正数，结果不好预期，我还未研究出是怎么执行的，
 * 结果会变得无意义。
 *
 * 当操作数是int时，位移运算符右侧的参数要进行模32的运算。如1<<35或1<<3或8是相同的。
 * 当操作数是long时，要进行模64操作。
 *
 * @author xiaoe
 */
public class BitOperate {
	public static void main(String[] args) {
		System.out.println("将2左移3位得：" + (2 << 3));
		System.out.println("将-2左移3位得：" + (-2 << 3));
		System.out.println("将3右移1位得：" + (3 >> 1));
		// 负数的右移是以补码来移动的，补码移动后再-1取反，得到数值。
		System.out.println("将-6右移1位得：" + (-6 >> 1));
		System.out.println("将-5右移2位得：" + (-5 >> 2));
		System.out.println("将-7右移3位得：" + (-7 >> 3));
		System.out.println("将-2右移3位得：" + (-2 >> 3));
		System.out.println("将4无符号右移3位得：" + (4 >>> 3));
		System.out.println("将-4无符号右移1位得：" + (-4 >>> 1));
		System.out.println("取13的二进制位的倒数第三位：" + shadowBit8(13));
	}

	/**
	 * 根据位与运算取得二进制位的倒数第三位。
	 *
	 * @param n
	 * @return
	 */
	static int shadowBit8(int n) {
		return (n & 8) / 8;
		// 也可以写成这样 return (n&(1<<3))>>3;
	}
}
