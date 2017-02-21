package com.zhaoyu.basic.exception;

import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * InputMismatchExceptionΪuncheckedException�������쳣���Խ��в�����
 * RuntimeException���ֱ�ӻ��߼�����඼�ǷǼ����쳣����Щ�쳣�����ɴ����⵽�ġ�
 */
public class UncheckExcpt {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		int number1;
		int number2;
		int sum;

		// ����������жϺֲܴڣ�����������ж���������ͣ����������б�InputMismatchException��
		// �ο�ScannerTest2.java
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
