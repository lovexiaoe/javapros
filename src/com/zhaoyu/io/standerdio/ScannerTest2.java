package com.zhaoyu.io.standerdio;

import java.util.Scanner;

public class ScannerTest2 {
	public static void main(String[] args) {
		// getAdd();
		showInput();
	}

	// ��������������⣬����nextInt��Ӧһ��hasNextInt,���Ժ���һ��û�еõ���顣
	static void getAdd() {
		Scanner cin = new Scanner(System.in);
		int a, b;
		while (cin.hasNextInt()) {
			a = cin.nextInt();
			b = cin.nextInt();
			System.out.println(a + b);
		}
	}

	// ���������������ַ���
	static void showInput() {
		Scanner cin = new Scanner(System.in);
		int b;

		while (cin.hasNext()) {
			if (!cin.hasNextInt()) {
				// ����������Σ��ͷ�nextInt��������һ��ѭ����
				String ss = cin.next();
			} else {
				b = cin.nextInt();
				System.out.println(b);
			}

		}
	}

}
