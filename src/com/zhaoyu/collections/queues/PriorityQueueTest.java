package com.zhaoyu.collections.queues;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.PriorityQueue;

/**
 * priorityQueue中的元素可以按任意的顺序插入，却总是按照排序的顺序进行检索。
 * 就是说，无论何时调用remove方法，总会获得当前队列中最小的元素。
 * priorityQueue使用的数据结构为堆。是一个可自我调整的二叉树。
 *
 * 作用PriorityQueue典型的例子就是任务调度。每一个任务有一个优先级。将优先级最高的任务从队列中删除。
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

		// 迭代查看时，不一定会按照最小顺序进行。
		System.out.println("迭代查看元素！");
		for (GregorianCalendar gregorianCalendar : queue) {
			System.out.println(gregorianCalendar.get(Calendar.YEAR));
		}

		// 在删除元素时，总是删除最小的元素。
		System.out.println("移除元素！");
		while (!queue.isEmpty()) {
			System.out.println(queue.remove().get(Calendar.YEAR));
		}
	}
}
