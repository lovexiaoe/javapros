package com.zhaoyu.basic.string;

//string�Ļ���������length(),charAt(),getChars()
public class StringBaseMethod {
	public static void main(String[] args) {
		String s1 = "hello there";
		char[] charArray = new char[5];
		// length()����
		System.out.printf("\nLength of s1: %d", s1.length());

		// ʹ��charAt�����ӡ
		System.out.print("\nThe string reversed is: ");
		for (int count = s1.length() - 1; count >= 0; count--)
			System.out.printf("%c ", s1.charAt(count));

		// getChars copy�ַ�����
		s1.getChars(0, 5, charArray, 0);

		System.out.print("\nThe character array is: ");
		for (char character : charArray)
			System.out.print(character);
		System.out.println();
	}
}
