package com.zhaoyu.io.standerdio;

import java.util.Scanner;

public class ScannerTest2 {
	public static void main(String[] args) {
		// getAdd();
		showInput();
	}

	// 这个输入会出现问题，两个nextInt对应一个hasNextInt,所以后面一个没有得到检查。
	static void getAdd() {
		Scanner cin = new Scanner(System.in);
		int a, b;
		while (cin.hasNextInt()) {
			a = cin.nextInt();
			b = cin.nextInt();
			System.out.println(a + b);
		}
	}

	// 单个输入整数型字符，
	static void showInput() {
		Scanner cin = new Scanner(System.in);
		int b;

		while (cin.hasNext()) {
			if (!cin.hasNextInt()) {
				// 如果不是整形，释放nextInt，进入下一个循环。
				String ss = cin.next();
			} else {
				b = cin.nextInt();
				System.out.println(b);
			}

		}
	}

}
