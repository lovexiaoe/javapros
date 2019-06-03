package com.zhaoyu.collections.maps;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 在java中，散列表用链表数组实现，每个链表都被称为桶（bucket）。想要查找表中对应的位置，就要先计算它的hashcode，
 * 然后与桶的总数取余，所得到的结果就是保存这个元素的桶的索引。
 * 如果某个对象的hashcode为76268，并且有128个桶，对象就应该保存在第108个桶中（76268除以128余108）。如果这个桶没有满。
 * 那么就将这个对象存到桶中。当然，有时候会遇到桶被占满的情况，这种情况是不可避免的，被称为散列冲突（hash collision）。
 *
 * 如果大致知道有多少个元素要被插入到散列表中，就可以设置桶数。通常，将桶数设置为预计元素个数的75%~150%。
 * 且最好设置成一个素数，以防键的集聚。标准类库使用的桶是2的幂，默认值为16.
 *
 * 当然不可能总知道需要存储的元素个数 ，如果散列表太满，就需要再散列（rehashed）。创建一个桶更多的表，并将原表的数据移到
 * 这个新表中。装填因子(load factor)决定何时对表进行rehash。默认的是0.75。
 *
 * hashMap可以使用null的key或者value。不同步，继承自AbstractMap，可以使用Collections.synchronizedMap方法包装一个hashMap。
 *
 * 在1.8以后对相同hashcode的链表做了查询优化，使用树结构(红黑树)存储，查找的时间复杂度降低。
 *
 * 此类的“collection 视图方法”所返回的迭代器都是快速失败 的
 *
 * @author xiaoE
 *
 * 可能获得map的视图，一共有3个视图
 * Set<K> keySet()
 * Collection<K> values()
 * Set<Map.Entry<K,V>> entrySet()
 *
 * @author xiaoE
 *
 */
public class HashMapTest {
	public static void main(String[] args) {

		Map<String, Employee> staff = new HashMap<String, Employee>();
		staff.put("001", new Employee("张三"));
		staff.put("002", new Employee("张四"));
		staff.put("003", new Employee("张五"));
		staff.put("004", new Employee("张六"));

		System.out.println(staff);

		// 删除元素
		staff.remove("002");
		// 添加已存在的元素。
		staff.put("004", new Employee("张七"));
		// 遍历key-value。
		for (Map.Entry<String, Employee> entry : staff.entrySet()) {
			String key = entry.getKey();
			Employee value = entry.getValue();
			System.out.println("key=" + key + ",value" + value);
		}
	}
}

class Employee {
	private String name;
	private double salary;

	public Employee(String n) {
		name = n;
		salary = 0;
	}

	@Override
	public String toString() {
		return "[name=" + name + ",salary=" + salary + "]";
	}
}
