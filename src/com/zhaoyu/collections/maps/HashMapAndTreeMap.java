package com.zhaoyu.collections.maps;

import java.util.HashMap;
import java.util.Map;

/**
 * 可能获得map的视图，一共有3个视图
 * Set<K> keySet()
 * Collection<K> values()
 * Set<Map.Entry<K,V>> entrySet()
 *
 * @author xiaoE
 *
 */
public class HashMapAndTreeMap {
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
