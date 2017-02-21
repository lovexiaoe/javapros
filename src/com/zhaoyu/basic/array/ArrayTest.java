package com.zhaoyu.basic.array;

import java.util.Arrays;
import java.util.Random;

public class ArrayTest {
	public static void main(String[] args) {
		// ��һ�����鸳ֵ����һ�����飬��������������ͬһ�����飬һ���ı仯��ı��һ����
		int[] a1 = { 1, 2, 3, 4 };
		int[] a2 = a1;
		// �����ʹ��ȫcopy,����Ҫ�õ�Arrays.copyOf����
		// ����С��ԭ���鳤�ȣ����ȡ��
		int[] a3 = Arrays.copyOf(a1, 2);
		System.out.println(a3.length);
		// ���ȴ���ԭ���鳤�ȣ�����Ĭ��ֵ��䡣
		int[] a4 = Arrays.copyOf(a1, 5);
		System.out.println(a4[4]);

		// ����Ҳ�����������ڼ�ȷ����С
		Random rd = new Random(100);
		int actualSize = rd.nextInt();
		int[] array2 = new int[actualSize];

		// ���������ʹ�ÿ������򷨶������������
		int[] luckyNumbers = { 1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008 };
		Arrays.sort(luckyNumbers);
		for (int i = 0; i < luckyNumbers.length; i++) {
			System.out.println(i + ":" + luckyNumbers[i]);
		}

	}
}
