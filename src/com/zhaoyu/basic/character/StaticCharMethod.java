package com.zhaoyu.basic.character;

public class StaticCharMethod {
	public static void main(String[] args) {

		// char c = 'A'; // get input character
		char c = '五';

		// display character info
		// 判断c是否在Unicode字符集中。
		System.out.printf("is defined: %b\n", Character.isDefined(c));
		// 是否数字
		System.out.printf("is digit: %b\n", Character.isDigit(c));
		// 是否能作为java标识符中的第一个字母。
		System.out.printf("is first character in a Java identifier: %b\n", Character.isJavaIdentifierStart(c));
		// 是否是标识符的一部分。
		System.out.printf("is part of a Java identifier: %b\n", Character.isJavaIdentifierPart(c));
		// 是否为字母
		System.out.printf("is letter: %b\n", Character.isLetter(c));
		// 是否字母或数字
		System.out.printf("is letter or digit: %b\n", Character.isLetterOrDigit(c));
		// 是否小写字母
		System.out.printf("is lower case: %b\n", Character.isLowerCase(c));
		// 是否大写字母
		System.out.printf("is upper case: %b\n", Character.isUpperCase(c));
		// 转换为大写
		System.out.printf("to upper case: %s\n", Character.toUpperCase(c));
		// 转换为小写
		System.out.printf("to lower case: %s\n", Character.toLowerCase(c));
	}
}
