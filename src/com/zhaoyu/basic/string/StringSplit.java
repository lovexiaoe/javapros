package com.zhaoyu.basic.string;

import java.util.Arrays;

public class StringSplit {
	public static void main(String[] args) {
		// ���߷ָ�����Ҫת��
		String[] skuIds1 = "100229005|100229006|100229007|100229010".split("\\|");

		// ʹ������ָ��ַ���
		String[] results = "1, 2, 3, 4, 5, 6, 7, 8".split(",\\s*");
		System.out.println(Arrays.toString(results));

		// ���ַ���ʹ�ÿո�ָ�󣬻���һ��Ԫ�ء�
		String s = "";
		String[] specParamsStr = s.split("[\\t \\n]+");
		for (String string : specParamsStr) {
			System.out.println(string);
		}
		System.out.println(specParamsStr.length);
	}
}
