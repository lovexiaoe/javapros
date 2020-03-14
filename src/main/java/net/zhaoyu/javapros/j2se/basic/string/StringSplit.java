package net.zhaoyu.javapros.j2se.basic.string;

import java.util.Arrays;

public class StringSplit {
	public static void main(String[] args) {
		// 竖线分隔符需要转义
		String[] skuIds1 = "100229005|100229006|100229007|100229010".split("\\|");

		// 使用正则分隔字符串
		String[] results = "1, 2, 3, 4, 5, 6, 7, 8".split(",\\s*");
		System.out.println(Arrays.toString(results));

		// 空字符串使用空格分割后，会有一个元素。
		String s = "";
		String[] specParamsStr = s.split("[\\t \\n]+");
		for (String string : specParamsStr) {
			System.out.println(string);
		}
		System.out.println(specParamsStr.length);
	}
}
