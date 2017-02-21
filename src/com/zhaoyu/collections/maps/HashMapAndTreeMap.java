package com.zhaoyu.collections.maps;

import java.util.HashMap;
import java.util.Map;

/**
 * ���ܻ��map����ͼ��һ����3����ͼ
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
		staff.put("001", new Employee("����"));
		staff.put("002", new Employee("����"));
		staff.put("003", new Employee("����"));
		staff.put("004", new Employee("����"));

		System.out.println(staff);

		// ɾ��Ԫ��
		staff.remove("002");
		// ����Ѵ��ڵ�Ԫ�ء�
		staff.put("004", new Employee("����"));
		// ����key-value��
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
