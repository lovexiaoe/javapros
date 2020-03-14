package net.zhaoyu.javapros.j2se.basic.math;

import java.math.BigInteger;

/**
 * BigInteger提供了一种任意精度的整型，所有的操作类似二进制补码的操作（就像int类型）
 *
 * BIgInteger模仿Integer的算法操作，并提供尽可能大的长度去存放结果。
 *
 * 实现了位操作的负数操作，移除了无符号右移，对于无限长的数来说这意义不大。
 *
 * 主要的字段为符号位signum，-1表示负数，0表示0,1表示正数。int[] mag，数值的存储，如一个long可以存放在mag[0]和mag[1]中。
 */
public class BigIntegerTest {
	public static void main(String[] args) {
		System.out.println("从2个数据中抽取1个的概率" + draw(2, 1) + "分之1");
		System.out.println("从490个数据中抽取60个的概率" + draw(490, 60) + "分之1");
	}

	/**
	 * 计算从k个数据中抽取j个的概率，计算n*(n-1)*(n-2)*...*(n-k+1)/(1*2*3*k)
	 *
	 * @param k
	 * @param j
	 * @return
	 */
	static BigInteger draw(int k, int j) {
		BigInteger bg = BigInteger.valueOf(1);
		for (int i = 1; i <= j; i++) {
			bg = bg.multiply(BigInteger.valueOf(k - i + 1)).divide(BigInteger.valueOf(i));
		}
		return bg;

	}
}
