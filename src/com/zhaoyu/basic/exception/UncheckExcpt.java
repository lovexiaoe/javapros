package com.zhaoyu.basic.exception;

import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * InputMismatchException为uncheckedException，所有异常可以进行捕获处理。
 * RuntimeException类的直接或者间接子类都是非检验异常。这些异常都是由代码检测到的。
 */
public class UncheckExcpt {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		int number1;
		int number2;
		int sum;

		// 这里的输入判断很粗糙，如果不进行判断输入的类型，则程序会运行报InputMismatchException错。
		// 参考ScannerTest2.java
		try {
			System.out.print("Enter first integer: ");
			number1 = input.nextInt();

			System.out.print("Enter second integer: ");
			number2 = input.nextInt();
			sum = number1 + number2;
			System.out.printf("Sum is %d\n", sum);
		} catch (InputMismatchException e) {
			System.out.println("exception occur");
		}

	}
}
