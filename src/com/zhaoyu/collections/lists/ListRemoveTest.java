package com.zhaoyu.collections.lists;

import java.util.LinkedList;
import java.util.List;

public class ListRemoveTest {
	public static void main(String[] args) {

		List<Integer> warePriceChanges = new LinkedList<Integer>();
		warePriceChanges.add(new Integer(1));
		warePriceChanges.add(new Integer(3));
		warePriceChanges.add(new Integer(2));

		System.out.println(warePriceChanges.size());

		// List��ɾ��һ��Ԫ�غ�size��仯��iteratorҲ�����±仯�����Բ��ܴ�ͷ��β�Ľ���ɾ����
		/*
		 * for (int j = 0; j < warePriceChanges.size(); j++) {
		 * System.out.println(j + "=" + warePriceChanges.get(j));
		 * warePriceChanges.remove(j);
		 * 
		 * }
		 */

		/*
		 * ������һ��ɾ��������һֱɾ��indexΪ0��Ԫ�ء�
		 */

		while (warePriceChanges.size() > 0) {
			Integer i2 = warePriceChanges.get(0);
			warePriceChanges.remove(0);
			System.out.println(i2);
		}

		System.out.println(warePriceChanges.size());
	}

}
