package com.zhaoyu.oop.interfaces;

import java.util.Arrays;

/**
 * һ�������Ҫ�������������ʵ��Comparable�ӿڣ�����дcompareTo�ӿڡ�
 *
 * @author xiaoe
 *
 */
public class ComparableTest {
	public static void main(String[] args) {

		Employee[] employees = new Employee[3];
		Employee e1 = new Employee(11, "����");
		Employee e2 = new Employee(32, "�ÿ�");
		Employee e3 = new Employee(28, "��˧");
		employees[0] = e1;
		employees[1] = e2;
		employees[2] = e3;
		// ���Employeeû��ʵ��Comparable�ӿڣ����������ʱ��ClassCastException����
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
	 * ���Ա�׼�涨�����������x��y��ʵ�ֱ����ܹ���֤sgn(x.compareTo(y))=-sgn(y.compareTo(x))
	 * x.compareTo(y)�׳��쳣��y.compareTo(x)ҲӦ���׳�һ���쳣��
	 * 
	 * ע�⿼�ǱȽ�����Ϊ�յ����
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Employee o) {
		return this.age - o.age;
	}

}
