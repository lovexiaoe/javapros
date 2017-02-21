package com.zhaoyu.utils.datecalendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class GregorianTest {
	public static void main(String[] args) {
		GregorianCalendar calendar1 = new GregorianCalendar();
		// ע��������·��Ǵ�0��ʼ�ġ�
		GregorianCalendar calendar2 = new GregorianCalendar(1992, 0, 1);
		GregorianCalendar calendar3 = new GregorianCalendar(1993, Calendar.JANUARY, 31, 22, 59, 59);
		// getTime������setTime��������calendar��date֮��ת����
		calendar1.setTime(new Date());
		System.out.println("����1��" + calendar1.getTime());
		System.out.println("����2��" + calendar2.getTime());
		System.out.println("����3��" + calendar3.getTime());
		// �õ���������,�����������¡�
		System.out.println("����3����Ϊ" + calendar3.get(Calendar.YEAR));
		calendar3.set(Calendar.MONTH, 6);
		System.out.println("����3����Ϊ" + calendar3.get(Calendar.MONTH));

		calendar3.add(Calendar.DATE, 1);
		System.out.println(calendar3.getTime());
		System.out.println("����3����Ϊ" + calendar3.get(Calendar.MONTH));

		System.out.println("===" + getExpireTime());
		lastDayOfYearAddOneDay();
		OneDayAdd24Hour();
		SetCalendar();
	}

	// ���㵽��������0�������
	public static Long getExpireTime() {
		Date time = new Date();
		// ���㵽��������12���������
		Calendar ca = new GregorianCalendar();
		ca.setTime(time);
		ca.add(Calendar.DATE, 1);
		ca.set(Calendar.HOUR_OF_DAY, 0);
		ca.set(Calendar.MINUTE, 0);
		ca.set(Calendar.SECOND, 0);

		Calendar ca2 = new GregorianCalendar();
		ca2.setTime(time);
		ca2.set(Calendar.HOUR_OF_DAY, 0);
		ca2.set(Calendar.MINUTE, 0);
		ca2.set(Calendar.SECOND, 0);
		System.out.println(time);
		System.out.println(ca.getTime());
		System.out.println(ca2.getTime());

		long mid = ca.getTimeInMillis() - time.getTime();
		System.out.println("----" + (ca.getTimeInMillis() - ca2.getTimeInMillis()) / 1000);
		return mid / 1000;
	}

	// һ������һ���һ�죬Ϊ�ڶ���ĵ�һ��
	public static void lastDayOfYearAddOneDay() {
		Date time = new Date();
		// ���㵽��������12���������
		Calendar ca = new GregorianCalendar();
		ca.setTime(time);
		ca.set(Calendar.MONTH, 12);
		ca.set(Calendar.DAY_OF_MONTH, 31);
		ca.add(Calendar.DAY_OF_YEAR, 1);
		System.out.println("lastDayOfYearAddOneDay:" + ca.getTime());
	}

	// һ������һ���һ�죬Ϊ�ڶ���ĵ�һ��
	public static void OneDayAdd24Hour() {
		Date time = new Date();
		// ���㵽��������12���������
		Calendar ca = new GregorianCalendar();
		ca.setTime(time);
		System.out.println("OneDay:" + ca.getTime());
		ca.add(Calendar.HOUR, -24);
		System.out.println("OneDayAdd-24Hour:" + ca.getTime());
		ca.add(Calendar.HOUR_OF_DAY, 24);
		System.out.println("OneDayAdd24HourAgain:" + ca.getTime());
	}

	public static void SetCalendar() {
		Calendar ca = new GregorianCalendar();
		ca.set(2016, Calendar.OCTOBER, 27, 0, 0, 0);
		System.out.println(new SimpleDateFormat("yyyyMMdd").format(ca.getTime()));
	}
}
