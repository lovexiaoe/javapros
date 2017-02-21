package com.zhaoyu.utils.datecalendar;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * ��ӡ���µ�ũ����
 * 
 * @author xiaoe
 *
 */
public class PrintOneMonthCalendar {
	public static void main(String[] args) {
		GregorianCalendar calendar = new GregorianCalendar();

		// ��ȡ�������·�����
		int today = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH);

		// �������ú���Ϊ1
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		// ��ȡ1�ŵ�������
		int weekday = calendar.get(Calendar.DAY_OF_WEEK);
		// ��ȡһ�ܵĵ�һ�������ڼ�
		int firstDayOfWeek = calendar.getFirstDayOfWeek();

		// �����һ�е�������
		int indent = 0;
		while (weekday != firstDayOfWeek) {
			indent++;
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			weekday = calendar.get(Calendar.DAY_OF_WEEK);
		}

		// ��ӡ����ͷ
		String[] weekdayName = new DateFormatSymbols().getWeekdays();
		do {
			System.out.printf("%4s", weekdayName[weekday]);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			weekday = calendar.get(Calendar.DAY_OF_WEEK);
		} while (weekday != firstDayOfWeek);
		System.out.println();

		// ��ӡ����
		for (int i = 0; i < indent; i++) {
			System.out.print("   ");
		}

		// ��1�ſ�ʼ��ӡ����
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		do {
			// ��ӡ����
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			System.out.printf("%3d", day);

			// ��ǽ���
			if (day == today) {
				System.out.print("*  ");
			} else {
				System.out.print("   ");
			}

			// ���õ���һ��
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			weekday = calendar.get(Calendar.DAY_OF_WEEK);

			// һ���껻��
			if (weekday == firstDayOfWeek) {
				System.out.println();
			}

		} while (calendar.get(Calendar.MONTH) == month);

		// �������ӡһ������
		if (weekday != firstDayOfWeek) {
			System.out.println();
		}
	}
}
