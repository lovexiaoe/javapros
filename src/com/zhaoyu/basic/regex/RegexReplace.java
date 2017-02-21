package com.zhaoyu.basic.regex;

public class RegexReplace {
	public static void main(String[] args) {
		// 角标系统中的匹配案例。
		String filenameStr = "A:1,B:2,C:3,BA:2";
		// 替换所有的"A：数字"为"a:3"
		System.out.println(filenameStr.replaceAll("A" + ":\\d", "a:3"));
		// 匹配"刘:223" 或者"asdf:-2"
		System.out.println("2刘:123".matches("[^:\\s]+:-?\\d+"));
		// 用｛｝限定长度。
		System.out.println("20170105,20170106".matches("^\\d{8},\\d{8}$"));
		System.out.println("asB".matches("-?[[a-zA-Z]\\d]{0,9}"));

		// 正则去空格
		System.out.println(" 29103	 ".replaceAll("(\\s)|([\r\n])", ""));

		// json匹配特定选项。
		String msg = "{'data':{'creator':null,'modified':'2016-08-19 15:35:37','orderFieldNextType':'ASC','pageSize':null,'status':1}}";
		System.out.println(msg.replaceFirst(",'orderFieldNextType':[^,]*,", ","));
		System.out.println(msg.replaceFirst(",'pageSize':[^,]*,", ","));

	}
}
