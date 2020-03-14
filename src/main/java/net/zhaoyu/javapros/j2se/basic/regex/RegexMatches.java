package net.zhaoyu.javapros.j2se.basic.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * pattern类表示一个正则表达式。
 * Matcher类同时包含正则表达式模式和一个要在其中搜索模式的CharSequence,
 * CharSequence是一个接口，String也实现了这个接口。
 */

/**
 * 下面这个程序用正则表达式匹配生日，生日不以4月出生，且姓以J开关
 *
 * @author xiaoE
 *
 */
public class RegexMatches {
	public static void main(String[] args) {
		// create regular expression
		Pattern expression = Pattern.compile("J.*\\d[0-35-9]-\\d\\d-\\d\\d");

		String string1 = "Jane's Birthday is 05-12-75\n" + "Dave's Birthday is 11-04-68\n"
				+ "John's Birthday is 04-28-73\n" + "Joe's Birthday is 12-17-77";

		// match regular expression to string and print matches
		Matcher matcher = expression.matcher(string1);

		// find方法，匹配到后继续查找，lookingAt方法总是从对象开始处进行搜索。
		while (matcher.find())
			System.out.println(matcher.group());
	}
}
