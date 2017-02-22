package com.zhaoyu.utils.datecalendar;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * 打印当月的农历。
 * 
 * @author xiaoe
 *
 */
public class PrintOneMonthCalendar {
	public static void main(String[] args) {
		GregorianCalendar calendar = new GregorianCalendar();

		// 获取号数和月份数。
		int today = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH);

		// 重新设置号数为1
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		// 获取1号的星期数
		int weekday = calendar.get(Calendar.DAY_OF_WEEK);
		// 获取一周的第一天是星期几
		int firstDayOfWeek = calendar.getFirstDayOfWeek();

		// 计算第一行的缩进数
		int indent = 0;
		while (weekday != firstDayOfWeek) {
			indent++;
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			weekday = calendar.get(Calendar.DAY_OF_WEEK);
		}

		// 打印星期头
		String[] weekdayName = new DateFormatSymbols().getWeekdays();
		do {
			System.out.printf("%4s", weekdayName[weekday]);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			weekday = calendar.get(Calendar.DAY_OF_WEEK);
		} while (weekday != firstDayOfWeek);
		System.out.println();

		// 打印缩进
		for (int i = 0; i < indent; i++) {
			System.out.print("   ");
		}

		// 从1号开始打印日历
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		do {
			// 打印号数
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			System.out.printf("%3d", day);

			// 标记今天
			if (day == today) {
				System.out.print("*  ");
			} else {
				System.out.print("   ");
			}

			// 设置到下一天
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			weekday = calendar.get(Calendar.DAY_OF_WEEK);

			// 一周完换行
			if (weekday == firstDayOfWeek) {
				System.out.println();
			}

		} while (calendar.get(Calendar.MONTH) == month);

		// 在下面打印一个空行
		if (weekday != firstDayOfWeek) {
			System.out.println();
		}
	}
}
