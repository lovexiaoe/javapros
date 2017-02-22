package com.zhaoyu.utils.datecalendar;

import java.util.Date;

public class DateTest {
	public static void main(String[] args) {
		System.out.println(new Date());
		// 1970年过了200ms;
		Date birthday = new Date(200);
		System.out.println(new Date(1000 * 3600 * 48));
		System.out.println(birthday);
		Date today = new Date();
		// after判断时间是否晚于另一个时间点。
		if (today.after(birthday)) {
			System.out.println("我已经降生！");
		} else {
			System.out.println("我还没有出生！");
		}
	}
}
