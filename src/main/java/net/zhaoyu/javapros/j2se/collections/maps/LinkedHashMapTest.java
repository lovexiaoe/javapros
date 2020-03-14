package net.zhaoyu.javapros.j2se.collections.maps;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LinkedHashMap和用来记录访问元素项的顺序。
 * LinkedHashMap相比HashMap，多了一个连接所有entry的双向链表。
 * 链表定义了map的iterator的顺序。 每次调用get或者put， 受到影响的条目将从双向链表的
 * 当前的位置删除，并放到双向链表的尾部（entry只是在双向链表中的位置发生了变化，在桶中的位置不会变化）
 * 实现了"最近最少使用"原则。
 *
 * 甚至可以构造一个高速缓存LinkedHashMap。
 *
 * LinkedHashSet类似于LinkedHashMap
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
