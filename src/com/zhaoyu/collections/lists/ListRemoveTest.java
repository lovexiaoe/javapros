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

		// List在删除一个元素后，size会变化，iterator也会重新变化，所以不能从头到尾的进行删除。
		/*
		 * for (int j = 0; j < warePriceChanges.size(); j++) {
		 * System.out.println(j + "=" + warePriceChanges.get(j));
		 * warePriceChanges.remove(j);
		 * 
		 * }
		 */

		/*
		 * 下面是一种删除方法，一直删除index为0的元素。
		 */

		while (warePriceChanges.size() > 0) {
			Integer i2 = warePriceChanges.get(0);
			warePriceChanges.remove(0);
			System.out.println(i2);
		}

		System.out.println(warePriceChanges.size());
	}

}
