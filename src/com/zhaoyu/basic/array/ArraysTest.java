package com.zhaoyu.basic.array;

import java.util.Arrays;

public class ArraysTest {
	public static void main(String[] args) {
		// ����
		double[] doubleArray = { 8.4, 9.3, 0.2, 7.9, 3.4 };
		Arrays.sort(doubleArray);
		System.out.printf("\ndoubleArray: ");

		for (double value : doubleArray)
			System.out.printf("%.1f ", value);

		// ��7�������
		int[] filledIntArray = new int[10];
		Arrays.fill(filledIntArray, 7);
		displayArray(filledIntArray, "filledIntArray");

		// ����copy
		int[] intArray = { 1, 2, 3, 4, 5, 6 };
		int[] intArrayCopy = new int[intArray.length];
		System.arraycopy(intArray, 0, intArrayCopy, 0, intArray.length);
		displayArray(intArray, "intArray");
		displayArray(intArrayCopy, "intArrayCopy");

		// �Ƚ��������
		boolean b = Arrays.equals(intArray, intArrayCopy);
		System.out.printf("\n\nintArray %s intArrayCopy\n", (b ? "==" : "!="));

		// �Ƚ� intArray �� filledIntArray �Ƿ����
		b = Arrays.equals(intArray, filledIntArray);
		System.out.printf("intArray %s filledIntArray\n", (b ? "==" : "!="));

		// ���ַ����� 5
		int location = Arrays.binarySearch(intArray, 5);
		if (location >= 0)
			System.out.printf("Found 5 at element %d in intArray\n", location);
		else
			System.out.println("5 not found in intArray");

		// ���ַ����� 8763
		location = Arrays.binarySearch(intArray, 8763);
		if (location >= 0)
			System.out.printf("Found 8763 at element %d in intArray\n", location);
		else
			System.out.println("8763 not found in intArray");

		// ���ַ�����7.9����doubleArray1�У���û���������Զ��ֲ��һ�������Ȼ�󷵻�����ǰ��λ�ã�����ı�ԭ���顣
		double[] doubleArray1 = { 8.4, 9.3, 0.2, 7.9, 3.4 };
		location = Arrays.binarySearch(doubleArray1, 7.9);
		System.out.printf("Found 7.9 at element %d in intArray\n", location);
		System.out.println("doubleArray1[2]:" + doubleArray1[2]);
	}

	// �����ӡ����
	public static void displayArray(int[] array, String description) {
		System.out.printf("\n%s: ", description);

		for (int value : array)
			System.out.printf("%d ", value);
	}
}
