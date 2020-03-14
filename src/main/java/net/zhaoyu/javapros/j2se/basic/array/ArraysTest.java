package net.zhaoyu.javapros.j2se.basic.array;

import java.util.Arrays;

public class ArraysTest {
	public static void main(String[] args) {
		// 排序
		double[] doubleArray = { 8.4, 9.3, 0.2, 7.9, 3.4 };
		Arrays.sort(doubleArray);
		System.out.printf("\ndoubleArray: ");

		for (double value : doubleArray)
			System.out.printf("%.1f ", value);

		// 用7填充数组
		int[] filledIntArray = new int[10];
		Arrays.fill(filledIntArray, 7);
		displayArray(filledIntArray, "filledIntArray");

		// 数组copy
		int[] intArray = { 1, 2, 3, 4, 5, 6 };
		int[] intArrayCopy = new int[intArray.length];
		System.arraycopy(intArray, 0, intArrayCopy, 0, intArray.length);
		displayArray(intArray, "intArray");
		displayArray(intArrayCopy, "intArrayCopy");

		// 比较数组相等
		boolean b = Arrays.equals(intArray, intArrayCopy);
		System.out.printf("\n\nintArray %s intArrayCopy\n", (b ? "==" : "!="));

		// 比较 intArray 和 filledIntArray 是否相等
		b = Arrays.equals(intArray, filledIntArray);
		System.out.printf("intArray %s filledIntArray\n", (b ? "==" : "!="));

		// 二分法查找 5
		int location = Arrays.binarySearch(intArray, 5);
		if (location >= 0)
			System.out.printf("Found 5 at element %d in intArray\n", location);
		else
			System.out.println("5 not found in intArray");

		// 二分法查找 8763
		location = Arrays.binarySearch(intArray, 8763);
		if (location >= 0)
			System.out.printf("Found 8763 at element %d in intArray\n", location);
		else
			System.out.println("8763 not found in intArray");

		// 二分法查找7.9，在doubleArray1中，并没有排序，所以二分查找会先排序，然后返回排序前的位置，不会改变原数组。
		double[] doubleArray1 = { 8.4, 9.3, 0.2, 7.9, 3.4 };
		location = Arrays.binarySearch(doubleArray1, 7.9);
		System.out.printf("Found 7.9 at element %d in intArray\n", location);
		System.out.println("doubleArray1[2]:" + doubleArray1[2]);
	}

	// 数组打印方法
	public static void displayArray(int[] array, String description) {
		System.out.printf("\n%s: ", description);

		for (int value : array)
			System.out.printf("%d ", value);
	}
}
