package com.zhaoyu.basic.string;

public class SubString {
	public static void main(String[] args) {
		String letters = "abcdefghijklmabcdefghijklm";

		// test substring methods
		System.out.printf("��20��ȡ \"%s\"\n", letters.substring(20));
		System.out.printf("%s \"%s\"\n", "��3��ʼ��ȡ��������6��", letters.substring(3, 6));

		// index ���substring��ȡ��
		String filenameStr = "A:1,B:2,C:3,BA:2";
		System.out.println(filenameStr.substring(0, filenameStr.indexOf(":")));
	}
}
