package com.zhaoyu.generic;

import java.util.ArrayList;

/**
 * ͨ�������
 *
 * ���г������޶���ͨ����������Ͷ���д�룬�����������޶���ͨ������Դӷ��Ͷ����ȡ��
 *
 * @author xiaoE
 *
 */
public class Wildcard {
	public static void main(String[] args) {
		// ���г������޶���ͨ���
		// ����������ʹ��<? extends Employee>���ܶ�Employee��������в�����
		ArrayList<?> wildList;
		ArrayList<Manager> maList = new ArrayList<Manager>();
		ArrayList<? extends Employee> emList = maList; // ok
		// wildList.add(new Manager()); // error

	}
}

class Employee {

}

class Manager extends Employee {
	// �����������޶���ͨ���
	public static ArrayList<? super Manager> getsub(Manager[] a, ArrayList<? super Manager> elist) {
		for (int i = 0; i < a.length; i++) {
			if (i < 2) {
				elist.add(a[i]);
			}
		}
		return elist;
	}
}

/**
 * Comparable�ӿڱ������һ���������ͣ�
 * ���԰�������ȡ����С ֵ �ķ������� Ϊ
 * public static <T extends Comparable<T>> T min(T[] a)
 *
 * <T extends Comparable<? super T>>�Ľ��͡�
 * GregorianCalendar ��Calendar�����࣬����Calendarʵ����Comparable<Calendar>�����GregorianCalendar
 * ʵ�ֵ���Comparable<Calendar>,������Comparable<GregorianCalendar>,����������£�������һ��
 * GregorianCalendar���������ʱ��Ӧ�ý����淽��д�ɣ�
 * public static <T extends Comparable<? super T>> T min(T[] a)
 * compareTo����д�ɣ�
 * int compareTo(? super T)
 */

