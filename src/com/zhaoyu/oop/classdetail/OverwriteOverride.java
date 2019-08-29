package com.zhaoyu.oop.classdetail;

public class OverwriteOverride {
	public static void main(String[] args) {

	}
}

class Father {
	int i;

	int add(int a, int b) {
		return a + b;
	}
}

class Son2 extends Father {
	// 名称相同，参数不同，是对add方法的重载。
	int add(int a) {
		return a + 1;
	}

	// 重写时，返回类型必须相同，下面方法会报错。
	/*@Override
	double add(int a, int b) {
		return a + b;
	}*/

	double add(int a, int b, int c) {
		return a + b + c;
	}

	// 重载时，不考虑返回类型，只考虑方法名和参数，
	// 方法名相同时，参数也相同 ，说明方法是一个方法，会报错。
	/*int add(int a, int b, int c) {
		return a + b + c;
	}*/
}
