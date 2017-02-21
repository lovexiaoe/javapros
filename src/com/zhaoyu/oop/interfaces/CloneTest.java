package com.zhaoyu.oop.interfaces;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * clone()������Object��protected������
 * ��һ���������cloneʱ�����е���ǳ���������ö���ı������ǿ��������õ�ַ��
 * Ҫ��������ε�copy������Ҫʵ��Cloneable�ӿڣ�����д clone������ʹ��public���Ρ�
 *
 * clone��Immutable�ӿڹ�ϵ���У�java��Ϊ�ɱ���� �Ͳ��ɱ����
 * �ɱ����У�Date��StringBuffer���Զ�����ȡ�
 * ���ɱ��ࣺString,Boolean,Byte,Integer,Character�ȡ�
 *
 * ��Բ��ɱ��࣬cloneʱ�Ͳ���������⡣
 * ��Կɱ��࣬��Ҫ��дclone������㿽�� ��
 *
 * Cloneable��һ��marker�ӿڣ����������κη�����clone��java��ʹ���в������á�
 * ������һ���������clone�����ӡ�
 *
 * @author xiaoe
 *
 */
public class CloneTest {
	public static void main(String[] args) {
		Employee2 original = new Employee2("�ǵ�", 6000.21, 56, new Date());
		Employee2 copy = null;
		try {
			copy = original.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		// string���ǲ��ɱ��࣬���Զ�string�ĸı䶼�����½��� ��
		System.out.println(copy.getName() == original.getName());
		copy.setName("����");
		copy.setSalary(2323.32);
		// Integer��Ҳ�ǲ��ɱ������ �����Զ�Age�ĸı��Stringһ����
		copy.addAge(22);
		// �ı�copy.date��ֵ Ҳ��ı�original��ֵ �������ڲ��ı� copy.date������ ������£������¸�ֵһ��Date����
		// ��˵�� Date������ǳ���� ��
		copy.setDate(2014, 3, 12);
		System.out.println("original: " + original);
		System.out.println("copy: " + copy);
	}

}

class Employee2 implements Cloneable {
	public Employee2(String n, double s, Integer i, Date date) {
		this.name = n;
		this.salary = s;
		this.age = i;
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public Integer getAge() {
		return age;
	}

	public void addAge(Integer age) {
		this.age += age;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(int year, int month, int day) {
		Date newHireDay = new GregorianCalendar(year, month - 1, day).getTime();
		this.date.setTime(newHireDay.getTime());
	}

	private String name;
	private double salary;
	private Integer age;
	private Date date;

	/*
	 * �����������object.clone��copy�����õı�����name��salary������age����copy�������õ�ַ��
	 * ������clone������һ���µ�age��
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Employee2 clone() throws CloneNotSupportedException {
		Employee2 cloned = (Employee2) super.clone();
		// cloned.age = new Integer(100);
		return cloned;
	}

	@Override
	public String toString() {
		return "name:" + this.name + ", salary:" + this.salary + ", age:" + this.age + ", Date:" + this.date;
	}

}
