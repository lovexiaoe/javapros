package com.zhaoyu.oop.interfaces;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 一个类如果要进行排序，则必须实现Comparable接口，并重写compareTo方法。
 * Comparator接口是集合类实现元素排序时的工具接口，如SortableList中定义的sort(Comparator<T>),方法有（compare(o1,o2)）
 *
 * @author xiaoe
 *
 */
public class ComparableTest {
	public static void main(String[] args) {
		Employee[] employees = new Employee[3];
		Employee e1 = new Employee(11, "好靓");
		Employee e2 = new Employee(32, "好俊");
		Employee e3 = new Employee(28, "真帅");
		employees[0] = e1;
		employees[1] = e2;
		employees[2] = e3;
		// 如果Employee没有实现Comparable接口，则会在运行时报ClassCastException错误。
		// com.zhaoyu.oop.interfaces.Employee cannot be cast to java.lang.Comparable
		Arrays.sort(employees);
		for (int i = 0; i < employees.length; i++) {
			System.out.print(employees[i].getAge() + ", ");
		}
		System.out.println();
	}
}

class Employee implements Comparable<Employee> {
	private int age;
	private String name;

	public Employee(int age, String name) {
		super();
		this.age = age;
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*
	 * 语言标准规定，对于任意的x和y，实现必须能够保证sgn(x.compareTo(y))=-sgn(y.compareTo(x))
	 * x.compareTo(y)抛出异常，y.compareTo(x)也应该抛出一个异常。
	 * 
	 * 注意考虑比较两方为空的情况
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Employee o) {
		return this.age - o.age;
	}

}
