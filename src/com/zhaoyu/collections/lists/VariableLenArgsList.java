package com.zhaoyu.collections.lists;

//�ɱ䳤���������
public class VariableLenArgsList {

	public static void main(String[] args) {
		double d1 = 10.0;
		double d2 = 20.0;
		double d3 = 30.0;
		muti2(d1, d2, d3);
		muti2(d1, d2);
	}

	public static void muti2(double... numbers) {
		System.out.println(numbers[1]);
		// �������ı��������ݡ�
		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = numbers[i] * 2;
		}
		// foreach������ı����������Ԫ�ء������ǳ���ֻ�Ƕ�d���в�����������ı�numbers����
		// for (double d : numbers) {
		// d = d * 2;
		// }
		System.out.println(numbers[1]);
	}

}
