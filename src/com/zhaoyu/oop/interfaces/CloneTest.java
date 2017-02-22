package com.zhaoyu.oop.interfaces;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * clone()方法是Object的protected方法。
 * 当一个对象进行clone时，进行的是浅拷贝，引用对象的变量都是拷贝的引用地址。
 * 要定义更深层次的copy，就需要实现Cloneable接口，并重写 clone方法，使用public修饰。
 *
 * clone和Immutable接口关系密切，java分为可变对象 和不可变对象。
 * 可变类有：Date，StringBuffer，自定义类等。
 * 不可变类：String,Boolean,Byte,Integer,Character等。
 *
 * 针对不可变类，clone时就不会产生问题。
 * 针对可变类，需要重写clone进行深层拷贝 。
 *
 * Cloneable是一个marker接口，并不具有任何方法。clone在java的使用中并不常用。
 * 下面是一个进行深层clone的例子。
 *
 * @author xiaoe
 *
 */
public class CloneTest {
	public static void main(String[] args) {
		Employee2 original = new Employee2("亚当", 6000.21, 56, new Date());
		Employee2 copy = null;
		try {
			copy = original.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		// string类是不可变类，所以对string的改变都会重新建立 。
		System.out.println(copy.getName() == original.getName());
		copy.setName("夏娃");
		copy.setSalary(2323.32);
		// Integer类也是不可变类对象 ，所以对Age的改变和String一样。
		copy.addAge(22);
		// 改变copy.date的值 也会改变original的值 。这是在不改变 copy.date的引用 的情况下（如重新赋值一个Date），
		// 这说明 Date类型是浅拷贝 。
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
	 * 在这个例子中object.clone会copy非引用的变量，name和salary，但是age变量copy的是引用地址，
	 * 所以在clone中设置一个新的age。
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
