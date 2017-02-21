package com.zhaoyu.basic.string;

//string的基本方法，length(),charAt(),getChars()
public class StringBaseMethod {
	public static void main(String[] args) {
		String s1 = "hello there";
		char[] charArray = new char[5];
		// length()方法
		System.out.printf("\nLength of s1: %d", s1.length());

		// 使用charAt反向打印
		System.out.print("\nThe string reversed is: ");
		for (int count = s1.length() - 1; count >= 0; count--)
			System.out.printf("%c ", s1.charAt(count));

		// getChars copy字符串。
		s1.getChars(0, 5, charArray, 0);

		System.out.print("\nThe character array is: ");
		for (char character : charArray)
			System.out.print(character);
		System.out.println();
	}
}
