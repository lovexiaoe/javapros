package com.zhaoyu.collections.sets;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * 树集是一个有序集合。可以任意顺序将元素插入到集合中。排序是使用树结构来实现的，
 * 当前使用的是红黑树。查找时间复杂度为log2 n次方。
 *
 * treeSet知道怎么排序，树集假定元素实现了Comparable接口。
 * 然而，使用Comparable接口定义排序有其局限性，对于一个给定的类，只能实现这个类一次。如果一个集合需要按照不同的
 * 策略来排序，那就不好实现。
 * 不过，我们可以将Comparator的对象传递给TreeSet等集合的构造方法，告诉这个集合按照Comparator对象定义的比较方式
 * 进行比较。
 *
 * @author xiaoE
 *
 */
public class TreeSetTest {
	public static void main(String[] args) {
		ItemComparator comp = new ItemComparator();
		// 这里也可以使用ItemComparator 的匿名类定义 。
		SortedSet<String> sortedSet = new TreeSet<String>(comp);
	}
}

class ItemComparator implements Comparator<String> {

	@Override
	public int compare(String o1, String o2) {
		return o1.compareTo(o2);
	}

}
