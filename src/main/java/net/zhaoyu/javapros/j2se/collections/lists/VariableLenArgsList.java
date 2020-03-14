package net.zhaoyu.javapros.j2se.collections.lists;

//可变长数组参数。
public class VariableLenArgsList {

	public static void main(String[] args) {
		double d1 = 10.0;
		double d2 = 20.0;
		double d3 = 30.0;
		muti2(d1, d2, d3);
		muti2(d1, d2);
	}

	public static void muti2(double... numbers) {
		System.out.println(numbers[1]);
		// 下面程序改变数组内容。
		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = numbers[i] * 2;
		}
		// foreach并不会改变数组里面的元素。下面是程序只是对d进行操作，并不会改变numbers本身。
		// for (double d : numbers) {
		// d = d * 2;
		// }
		System.out.println(numbers[1]);
	}

}
