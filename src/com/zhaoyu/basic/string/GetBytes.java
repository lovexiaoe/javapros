package com.zhaoyu.basic.string;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/*
 * 该程序说明getBytes的使用。
 */
public class GetBytes {
	public static void main(String[] args) {
		// 各种编码情况下，getBytes的长度有所不同。
		System.out.println("============================");
		String str = "中";
		byte[] a = str.getBytes();
		try {
			a = str.getBytes("GBK");
			System.out.println("getBytesGBK： " + a.length);
			byte[] b = str.getBytes("UTF-8");
			System.out.println("getBytesUTF8: " + b.length);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 获取默认编码。
		System.out.println("DefaultEncode: " + Charset.defaultCharset());
		System.out.println("getBytesDefault: " + a.length);

		System.out.println("============================");
		// 按照byte宽度截取字符串
		String s1 = "q1要f2中，d在23中国人";
		System.out.println(subStr(s1, 7));
		System.out.println(subStr(s1, 8));
		System.out.println(subStr(s1, -2));
		System.out.println(subStr(s1, 50));
	}

	/*
	 * 根据字节长度进行截取。不能出现截取半个字符的情况。
	 *  "s".getBytes占一个字节，"林".getBytes在默认编码情况下占两个字节。
	 */
	public static String subStr(String str, int i) {
		if (i < 1) {
			return "";
		}
		int chlen = 0;
		int bytelen = 0;
		int strlen = 0;
		for (int j = 0; j < str.length(); j++) {
			chlen = Character.toString(str.charAt(j)).getBytes().length;
			bytelen = bytelen + chlen;
			if (bytelen <= i) {
				strlen = strlen + 1;
			} else {
				break;
			}
		}
		return str.substring(0, strlen);
	}
}
