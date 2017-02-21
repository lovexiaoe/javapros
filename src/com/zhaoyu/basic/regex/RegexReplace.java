package com.zhaoyu.basic.regex;

public class RegexReplace {
	public static void main(String[] args) {
		// �Ǳ�ϵͳ�е�ƥ�䰸����
		String filenameStr = "A:1,B:2,C:3,BA:2";
		// �滻���е�"A������"Ϊ"a:3"
		System.out.println(filenameStr.replaceAll("A" + ":\\d", "a:3"));
		// ƥ��"��:223" ����"asdf:-2"
		System.out.println("2��:123".matches("[^:\\s]+:-?\\d+"));
		// �ã����޶����ȡ�
		System.out.println("20170105,20170106".matches("^\\d{8},\\d{8}$"));
		System.out.println("asB".matches("-?[[a-zA-Z]\\d]{0,9}"));

		// ����ȥ�ո�
		System.out.println(" 29103	 ".replaceAll("(\\s)|([\r\n])", ""));

		// jsonƥ���ض�ѡ�
		String msg = "{'data':{'creator':null,'modified':'2016-08-19 15:35:37','orderFieldNextType':'ASC','pageSize':null,'status':1}}";
		System.out.println(msg.replaceFirst(",'orderFieldNextType':[^,]*,", ","));
		System.out.println(msg.replaceFirst(",'pageSize':[^,]*,", ","));

	}
}
