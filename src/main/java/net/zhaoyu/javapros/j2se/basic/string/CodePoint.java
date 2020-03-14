package net.zhaoyu.javapros.j2se.basic.string;

public class CodePoint {
	public static void main(String[] args) {
		// 𐐷 是一个辅助字符，需要2个代码点长度来表示，即2个utf16的长度 。
		String str = "zhe 𐐷𐐷is 中会";
		char blank = ' ';
		// charAt返回字节。
		System.out.println("第四个字节是空格：" + (str.charAt(3) == blank));
		System.out.println("第8个字节：" + str.charAt(7));

		// 得到代码点数量
		System.out.println("str的代码点共：" + str.codePointCount(0, str.length()));

		// 得到第6个代码点
		int index = str.offsetByCodePoints(0, 6);
		int cp = str.codePointAt(index);
		System.out.println("第6个代码点  " + cp);

		// --
		System.out.println("遍历str，查看每一个代码点");
		for (int i = 0; i < str.codePointCount(0, str.length());) {
			int codepoint = str.codePointAt(i);
			// 判断代码点是否是辅助代码点。
			if (Character.isSupplementaryCodePoint(cp)) {
				i += 2;
			} else
				i++;
			System.out.print(codepoint + " ");
		}
		/*
		 * 第四个字节是空格：true
		 * 第8个字节：?
		 */
	}
}
