package com.zhaoyu.basic.random;

import java.util.Random;

public class RandomIntegers {
	public static void main(String[] args) {
		// �����������
		Random randomNumbers = new Random();
		int face;

		// loop 20 times
		for (int counter = 1; counter <= 20; counter++) {
			// ���ɴ�1��6�������
			face = 1 + randomNumbers.nextInt(6);

			System.out.printf("%d  ", face);
			// ��������5����ӡһ�С�
			if (counter % 5 == 0)
				System.out.println();
		}
	}
}
