package com.zhaoyu.utils.datecalendar;

import java.util.Date;

public class DateTest {
	public static void main(String[] args) {
		System.out.println(new Date());
		// 1970�����200ms;
		Date birthday = new Date(200);
		System.out.println(new Date(1000 * 3600 * 48));
		System.out.println(birthday);
		Date today = new Date();
		// after�ж�ʱ���Ƿ�������һ��ʱ��㡣
		if (today.after(birthday)) {
			System.out.println("���Ѿ�������");
		} else {
			System.out.println("�һ�û�г�����");
		}
	}
}
