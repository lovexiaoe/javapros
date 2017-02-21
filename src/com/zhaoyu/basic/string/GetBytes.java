package com.zhaoyu.basic.string;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/*
 * �ó���˵��getBytes��ʹ�á�
 */
public class GetBytes {
	public static void main(String[] args) {
		// ���ֱ�������£�getBytes�ĳ���������ͬ��
		System.out.println("============================");
		String str = "��";
		byte[] a = str.getBytes();
		try {
			a = str.getBytes("GBK");
			System.out.println("getBytesGBK�� " + a.length);
			byte[] b = str.getBytes("UTF-8");
			System.out.println("getBytesUTF8: " + b.length);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// ��ȡĬ�ϱ��롣
		System.out.println("DefaultEncode: " + Charset.defaultCharset());
		System.out.println("getBytesDefault: " + a.length);

		System.out.println("============================");
		// ����byte��Ƚ�ȡ�ַ���
		String s1 = "q1Ҫf2�У�d��23�й���";
		System.out.println(subStr(s1, 7));
		System.out.println(subStr(s1, 8));
		System.out.println(subStr(s1, -2));
		System.out.println(subStr(s1, 50));
	}

	/*
	 * �����ֽڳ��Ƚ��н�ȡ�����ܳ��ֽ�ȡ����ַ��������
	 *  "s".getBytesռһ���ֽڣ�"��".getBytes��Ĭ�ϱ��������ռ�����ֽڡ�
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
