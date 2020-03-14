package net.zhaoyu.javapros.j2se.collections.maps;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * 在map中有些值在使用后不会再去调用 ，而垃圾回收机制不能从表中删除它，需要由程序负责从活动的map中删除它，
 * 或者使用WeakHashMap完成这件事情。
 * WeakHashMap使用弱引用（weak references）保存键。当这种弱键（weak keys）不再被使用(没有其它对象引用)时，
 * 垃圾回收器就有机会对它进行回收，回收后，这个键的键值对会自动被删除。
 *
 * 该map允许key 和 value为空，线程不安全。
 *
 * @author xiaoE
 *
 */
public class WeakHashMapTest {
	public static void main(String[] args) {
		Map<String, Object> map = new WeakHashMap();
		map.put("111", new Integer(2));
		map.put("111", new Integer(3));
		map.put("222", new Integer(4));
		System.out.println(map.get("222"));
		System.out.println("长度：" + map.size() + ", 内容" + map);
	}
}
