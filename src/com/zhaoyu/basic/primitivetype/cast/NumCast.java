package com.zhaoyu.basic.primitivetype.cast;

/**
 * 数值在强制转换成int时，会截断小数后面的部分，需要得到舍入值 ，需要使用Math.round。
 *
 * @author xiaoe
 *
 */
public class NumCast {
	public static void main(String[] args) {
		double x = 9.8993;
		int nx = (int) x;
		System.out.println("9.8993转换成int:" + nx);
		nx = (int) Math.round(x);
		System.out.println("9.8993四舍五入后:" + nx);
	}
}
