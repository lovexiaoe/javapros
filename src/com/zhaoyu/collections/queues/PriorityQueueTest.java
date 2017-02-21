package com.zhaoyu.collections.queues;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.PriorityQueue;

/**
 * priorityQueue�е�Ԫ�ؿ��԰������˳����룬ȴ���ǰ��������˳����м�����
 * ����˵�����ۺ�ʱ����remove�������ܻ��õ�ǰ��������С��Ԫ�ء�
 * priorityQueueʹ�õ����ݽṹΪ�ѡ���һ�������ҵ����Ķ�������
 *
 * ����PriorityQueue���͵����Ӿ���������ȡ�ÿһ��������һ�����ȼ��������ȼ���ߵ�����Ӷ�����ɾ����
 *
 * @author xiaoE
 *
 */
public class PriorityQueueTest {
	public static void main(String[] args) {
		PriorityQueue<GregorianCalendar> queue = new PriorityQueue<GregorianCalendar>();
		queue.add(new GregorianCalendar(1906, Calendar.DECEMBER, 9));
		queue.add(new GregorianCalendar(1815, Calendar.DECEMBER, 10));
		queue.add(new GregorianCalendar(1903, Calendar.DECEMBER, 3));
		queue.add(new GregorianCalendar(1910, Calendar.JUNE, 22));

		// �����鿴ʱ����һ���ᰴ����С˳����С�
		System.out.println("�����鿴Ԫ�أ�");
		for (GregorianCalendar gregorianCalendar : queue) {
			System.out.println(gregorianCalendar.get(Calendar.YEAR));
		}

		// ��ɾ��Ԫ��ʱ������ɾ����С��Ԫ�ء�
		System.out.println("�Ƴ�Ԫ�أ�");
		while (!queue.isEmpty()) {
			System.out.println(queue.remove().get(Calendar.YEAR));
		}
	}
}
