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
	// ������ͬ��������ͬ���Ƕ�add���������ء�
	int add(int a) {
		return a + 1;
	}

	// ��дʱ������Ҫ�͸���ķ�������ͬ�ķ������͡�
	@Override
	double add(int a, int b) {

	}

	double add(int a, int b, int c) {
		return a + b + c;
	}

	// ��������ͬʱ������Ҳ��ͬ ��˵��������һ���������ᱨ��
	int add(int a, int b, int c) {
		return a + b + c;
	}
}
