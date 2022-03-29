package net.zhaoyu.javapros.j2se.basic.array;

import java.util.Arrays;
import java.util.Random;

public class ArrayTest {
	public static void main(String[] args) {
		// 将一个数组赋值给另一个数组，这两个变量引用同一个数组，一个的变化会改变别一个。
		int[] a1 = { 1, 2, 3, 4 };
		int[] a2 = a1;
		// 如果想使用全copy,就需要用到Arrays.copyOf方法
		// 长度小于原数组长度，则截取。
		int[] a3 = Arrays.copyOf(a1, 2);
		System.out.println(a3.length);
		// 长度大于原数组长度，则用默认值填充。
		int[] a4 = Arrays.copyOf(a1, 5);
		System.out.println(a4[4]);

		// 数组的排序使用快速排序法对数组进行排序
		int[] luckyNumbers = { 1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008 };
		Arrays.sort(luckyNumbers);
		for (int i = 0; i < luckyNumbers.length; i++) {
			System.out.println(i + ":" + luckyNumbers[i]);
		}
		Class arrClazz = luckyNumbers.getClass();
		System.out.println(arrClazz);
	}
}
