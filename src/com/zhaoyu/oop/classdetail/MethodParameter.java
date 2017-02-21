package com.zhaoyu.oop.classdetail;

/**
 * ������������˵����java����������ʹ�õĶ�ֵ���� ������ı��κβ������������� ��
 *
 * @author xiaoe
 *
 */
public class MethodParameter {
	public static void main(String[] args) {
		// ֵ�����Ǵ��ݵ�double1�Ŀ���������û�иı�double1
		double double1 = 2.45;
		tripleValue(double1);
		System.out.println("double1 3����" + double1);

		// �������Ըı���������״̬ ��
		Employee xiaoming = new Employee("xiaoming", 100.2);
		xiaoming.setSalary(200.4);
		System.out.println("xiaoming��нˮ���ӵ�" + xiaoming.getSalary());

		// �������ܽ��µĶ��󸳸�������������ö��󴫵ݵ�Ҳ�����ö���ĵ�ַ������
		Employee laowang = new Employee("laowang", 150.55);
		System.out.println("����ǰ��laowang��name��" + laowang.getName());
		System.out.println("����ǰ��xiaoming��name" + xiaoming.getName());
		swap(xiaoming, laowang);
		System.out.println("������laowang��name��" + laowang.getName());
		System.out.println("������xiaoming��name" + xiaoming.getName());

	}

	static double tripleValue(double x) {
		return x * 3;
	}

	static void swap(Employee x, Employee y) {
		Employee temp = x;
		x = y;
		y = temp;
		System.out.println("����������x=" + x.getName());
		System.out.println("����������y=" + y.getName());
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
