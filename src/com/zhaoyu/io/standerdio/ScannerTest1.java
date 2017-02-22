package com.zhaoyu.io.standerdio;

import java.util.Scanner;

//简单的输入，程序不健壮，运行时会报错。
public class ScannerTest1 {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		int number1;
		int number2;
		int sum;

		// 这里的输入判断很粗糙，如果不进行判断输入的类型，则程序会运行报InputMismatchException错。
		// 参考ScannerTest2.java
		System.out.print("Enter first integer: ");
		number1 = input.nextInt();

		System.out.print("Enter second integer: ");
		number2 = input.nextInt();

		sum = number1 + number2;

		System.out.printf("Sum is %d\n", sum);
	}
}
