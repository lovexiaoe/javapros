package com.zhaoyu.basic.primitivetype.cast;

/**
 * ��ֵ��ǿ��ת����intʱ����ض�С������Ĳ��֣���Ҫ�õ�����ֵ ����Ҫʹ��Math.round��
 *
 * @author xiaoe
 *
 */
public class NumCast {
	public static void main(String[] args) {
		double x = 9.8993;
		int nx = (int) x;
		System.out.println("9.8993ת����int:" + nx);
		nx = (int) Math.round(x);
		System.out.println("9.8993���������:" + nx);
	}
}
