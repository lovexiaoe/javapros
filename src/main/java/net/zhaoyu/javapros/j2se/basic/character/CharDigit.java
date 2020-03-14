package net.zhaoyu.javapros.j2se.basic.character;

public class CharDigit {
	public static void main(String[] args) {
		int a = 13;
		// 将数字转换成16进制，并打印成字符。
		System.out.printf("%s\n", Character.forDigit(a, 16));

		char c = 'B';
		// 将字符转换成16进制并打印成数字。
		System.out.printf("%s\n", Character.digit(c, 16));
	}
}
