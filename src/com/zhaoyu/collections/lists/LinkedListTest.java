package com.zhaoyu.collections.lists;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * ��java�����е�������˫�����ӵġ�
 *
 * �ӿ�Iterator��û��add�����������ӽӿ�ListIterator������add������
 * ����ListIterator���������������������������������
 *
 * interface ListIterator<E> extends Iterator<E>
 * {
 * void add(E element); //add�����ڵ�����λ��֮ǰ��� һ���¶��� ��
 * ...
 * E previous()
 * boolean hasPrevious()
 * }
 *
 * @author xiaoE
 *
 */
public class LinkedListTest {
	public static void main(String[] args) {

		List<Integer> list = new LinkedList<Integer>();
		list.add(2);
		list.add(1);
		System.out.println(list.get(0));

		TestModificationException();
	}

	/**
	 * iterator�ܼ�⵽Ԫ�ص��޸ģ����iterator�������ļ��ϱ���һ��iterator���߼��������޸ģ��ͻ��׳�
	 * ModificationException�쳣��
	 *
	 */
	public static void TestModificationException() {
		List<String> li = new ArrayList<String>();
		li.add("1111");
		li.add("2222");
		li.add("3333");
		li.add("4444");
		// contains�������Լ���ĳ��ֵ���(equals)�Ķ����Ƿ�������б��С�
		System.out.println(li.contains("4444"));
		li.add("4444");
		System.out.println(li);
		ListIterator<String> i1 = li.listIterator();
		ListIterator<String> i2 = li.listIterator();
		i1.next();
		i1.remove();
		i2.next(); // throws java.util.ConcurrentModificationException
	}
}
