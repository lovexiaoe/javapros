package com.zhaoyu.collections.lists;

import java.util.ArrayList;

/**
 * 虽然数组也可以在运行时确定数组的大小，但是在数组初始化后，想改变就不太容易了，
 * ArrayList在增加或者删除元素时，具有自动调节数组容量的功能 。而不需要为此编写任何代码。
 *
 * ArrayList的插入和删除都需要移动大量的元素，所以修改效率低。
 *
 * @author xiaoe
 *
 */
public class ArryList {
	public static void main(String[] args) {
		// ArrayList可以在初始化时设置初始容量 ，
		ArrayList<Integer> list = new ArrayList<Integer>(100);
		list.add(1);
		list.add(3);
		list.add(8);
		// size()方法返回结果为3
		System.out.println(list.size());

		// 如果确定数组列表的大小不再发生变化，可以调用trimToSize方法，让列表未占用的空间回收。
		// 但是如果想再修改列表，就需要花费较多资源。
		// list.trimToSize();

		// 可以使用get方法得到列表中的元素。下标从0开始
		System.out.println(list.get(1));

		// 使用set设置元素的值 。
		list.set(0, 23);
		System.out.println(list.get(0));

		// 除了在尾部插入数据之外，还可以在指定的序列插入。
		list.add(0, 11);
		// ArrayList重写了toString方法。
		System.out.println(list);

		// 也可以删除指定的元素
		list.remove(1);
		System.out.println(list);

		// subList使用。
		list.clear();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		System.out.println(list.subList(2, 4));
	}

}
