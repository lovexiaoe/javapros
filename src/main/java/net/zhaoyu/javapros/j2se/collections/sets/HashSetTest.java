package net.zhaoyu.javapros.j2se.collections.sets;

import java.util.HashSet;
import java.util.Set;

/**
 * hashset是一个没有重复元素的集合。底层使用hashMap来实现。value都放入new Object();
 *
 * set借口提供基本的add,remove,contains，size方法，并且不是同步的，可以使用 Collections.synchronizedSet 进行包装。
 * 和HashMap一样，次类的iterator 方法返回的迭代器是快速失败。
 *
 * @author xiaoE
 *
 */
public class HashSetTest {
	public static void main(String[] args) {
		Set<String> set = new HashSet<String>();
		set.add("1111");
		set.add("2222");
		set.add("1111");// 重复添加，覆盖原来的1111。集合中仍然有两个元素。
		System.out.println(set.size());
	}
}
