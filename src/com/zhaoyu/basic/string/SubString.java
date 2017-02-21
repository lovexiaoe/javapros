package com.zhaoyu.basic.string;

public class SubString {
	public static void main(String[] args) {
		String letters = "abcdefghijklmabcdefghijklm";

		// test substring methods
		System.out.printf("从20截取 \"%s\"\n", letters.substring(20));
		System.out.printf("%s \"%s\"\n", "从3开始截取，不包含6：", letters.substring(3, 6));

		// index 结合substring截取。
		String filenameStr = "A:1,B:2,C:3,BA:2";
		System.out.println(filenameStr.substring(0, filenameStr.indexOf(":")));
	}
}
