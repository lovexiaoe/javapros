package com.zhaoyu.basic.character;

public class CharDigit {
	public static void main(String[] args) {
		int a = 13;
		// ������ת����16���ƣ�����ӡ���ַ���
		System.out.printf("%s\n", Character.forDigit(a, 16));

		char c = 'B';
		// ���ַ�ת����16���Ʋ���ӡ�����֡�
		System.out.printf("%s\n", Character.digit(c, 16));
	}
}
