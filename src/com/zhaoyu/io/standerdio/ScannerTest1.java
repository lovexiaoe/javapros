package com.zhaoyu.io.standerdio;

import java.util.Scanner;

//�򵥵����룬���򲻽�׳������ʱ�ᱨ��
public class ScannerTest1 {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		int number1;
		int number2;
		int sum;

		// ����������жϺֲܴڣ�����������ж���������ͣ����������б�InputMismatchException��
		// �ο�ScannerTest2.java
		System.out.print("Enter first integer: ");
		number1 = input.nextInt();

		System.out.print("Enter second integer: ");
		number2 = input.nextInt();

		sum = number1 + number2;

		System.out.printf("Sum is %d\n", sum);
	}
}
