package com.zhaoyu.basic.math;

import java.math.BigInteger;

public class Bignumber {
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
