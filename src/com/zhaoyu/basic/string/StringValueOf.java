package com.zhaoyu.basic.string;

/*
 * java中的每个Object方法都有一个toString方法，但是这种方法不能用于基本类型。
 * String提供了valueOf方法，将任何类型的参数转换为一个String对象。
 */
public class StringValueOf {
	public static void main(String[] args) {
		char[] charArray = { 'a', 'b', 'c', 'd', 'e', 'f' };
		boolean booleanValue = true;
		char characterValue = 'Z';
		int integerValue = 7;
		long longValue = 10000000000L; // L suffix indicates long
		float floatValue = 2.5f; // f indicates that 2.5 is a float
		double doubleValue = 33.333; // no suffix, double is default
		Object objectRef = "hello"; // assign string to an Object reference

		// 将一个数组转换成字符串。
		System.out.printf("char array = %s\n", String.valueOf(charArray));
		// 将数组的一部分转换为字符串。
		System.out.printf("part of char array = %s\n", String.valueOf(charArray, 3, 3));
		// boolean，char,...转换为字符串。
		System.out.printf("boolean = %s\n", String.valueOf(booleanValue));
		System.out.printf("char = %s\n", String.valueOf(characterValue));
		System.out.printf("int = %s\n", String.valueOf(integerValue));
		System.out.printf("long = %s\n", String.valueOf(longValue));
		System.out.printf("float = %s\n", String.valueOf(floatValue));
		System.out.printf("double = %s\n", String.valueOf(doubleValue));
		// Object本身有toString方法，可以转为字体串。
		System.out.printf("Object = %s\n", String.valueOf(objectRef));
	}
}
