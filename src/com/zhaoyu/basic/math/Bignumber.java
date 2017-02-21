package com.zhaoyu.basic.math;

import java.math.BigInteger;

public class Bignumber {
	public static void main(String[] args) {
		System.out.println("��2�������г�ȡ1���ĸ���" + draw(2, 1) + "��֮1");
		System.out.println("��490�������г�ȡ60���ĸ���" + draw(490, 60) + "��֮1");
	}

	/**
	 * �����k�������г�ȡj���ĸ��ʣ�����n*(n-1)*(n-2)*...*(n-k+1)/(1*2*3*k)
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
