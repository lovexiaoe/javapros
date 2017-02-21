package com.zhaoyu.collections.maps;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LinkedHashMap和LinkedHashSet用来记录插入元素项的顺序。
 * 链表散列表将用访问顺序，而不是插入顺序，对映射表条目进行 迭代。 每次调用get或者put，
 * 受到影响的条目将从当前的位置删除，并放到链表的尾部（对应的桶不会变化，只是第个元素在桶中的位置发生变化。）
 * 实现了"最近最少使用"原则。
 *
 * 甚至可以构造一个高速缓存LinkedHashMap。
 *
 * @author xiaoE
 *
 */
public class LinkedHashMapTest {
	public static void main(String[] args) {
		Map staff = new LinkedHashMap();
		staff.put("001", "村一");
		staff.put("002", "村二");
		staff.put("003", "村三");
		staff.put("004", "村四");
		Iterator<String> it = staff.keySet().iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}

		// 下面构建一个可以存入100个元素的高速缓存。
		Map cache = new LinkedHashMap(128, 0.75F, true) {
			@Override
			protected boolean removeEldestEntry(Map.Entry eldest) {
				return size() > 100;
			}
		};
	}
}
