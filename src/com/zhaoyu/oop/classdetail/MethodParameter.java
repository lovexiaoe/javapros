package com.zhaoyu.oop.classdetail;

/**
 * 下例例子用于说明，java方法调用中使用的都值调用 ，不会改变任何参数变量的内容 。
 *
 * @author xiaoe
 *
 */
public class MethodParameter {
	public static void main(String[] args) {
		// 值调用是传递的double1的拷贝。所以没有改变double1
		double double1 = 2.45;
		tripleValue(double1);
		System.out.println("double1 3倍后：" + double1);

		// 方法可以改变对象参数的状态 。
		Employee xiaoming = new Employee("xiaoming", 100.2);
		xiaoming.setSalary(200.4);
		System.out.println("xiaoming的薪水增加到" + xiaoming.getSalary());

		// 方法不能将新的对象赋给对象参数，引用对象传递的也是引用对象的地址拷贝。
		Employee laowang = new Employee("laowang", 150.55);
		System.out.println("互换前，laowang的name：" + laowang.getName());
		System.out.println("互换前，xiaoming的name" + xiaoming.getName());
		swap(xiaoming, laowang);
		System.out.println("互换后，laowang的name：" + laowang.getName());
		System.out.println("互换后，xiaoming的name" + xiaoming.getName());

	}

	static double tripleValue(double x) {
		return x * 3;
	}

	static void swap(Employee x, Employee y) {
		Employee temp = x;
		x = y;
		y = temp;
		System.out.println("方法结束后x=" + x.getName());
		System.out.println("方法结束后y=" + y.getName());
	}

	static class Employee {
		private String name;
		private double salary;

		public Employee(String n, double s) {
			this.name = n;
			this.salary = s;
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

	}
}
