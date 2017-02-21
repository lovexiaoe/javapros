package com.zhaoyu.basic.random;

import java.util.Random;

public class RandomIntegers {
	public static void main(String[] args) {
		// 随机数生成器
		Random randomNumbers = new Random();
		int face;

		// loop 20 times
		for (int counter = 1; counter <= 20; counter++) {
			// 生成从1到6的随机数
			face = 1 + randomNumbers.nextInt(6);

			System.out.printf("%d  ", face);
			// 订数器，5个打印一行。
			if (counter % 5 == 0)
				System.out.println();
		}
	}
}
