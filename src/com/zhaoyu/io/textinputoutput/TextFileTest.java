package com.zhaoyu.io.textinputoutput;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * 该类操作文本内容写入和输出。以文本方式存储对象。
 *
 * @author xiaoE
 *
 */
public class TextFileTest {
	public static void main(String[] args) {
		Employee[] staff = new Employee[3];
		staff[0] = new Employee("赵2", 75000, 1984, 12, 14);
		staff[1] = new Employee("王八", 50000, 1988, 5, 4);
		staff[2] = new Employee("刘6", 35000, 1990, 6, 13);
		try {
			// 写入文本数据。
			PrintWriter out = new PrintWriter("employee.dat");
			writeData(staff, out);
			out.close();

			// 读取文件数据。
			Scanner in = new Scanner(new FileReader("employee.dat"));
			Employee[] newStaff = readData(in);
			in.close();
			for (Employee employee : newStaff) {
				System.out.println(employee);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void writeData(Employee[] employees, PrintWriter out) {
		out.println(employees.length);
		for (Employee employee : employees) {
			employee.writeData(out);
		}
	}

	private static Employee[] readData(Scanner in) {
		int n = in.nextInt();
		in.nextLine();
		Employee[] employees = new Employee[n];
		for (int i = 0; i < n; i++) {
			employees[i] = new Employee();
			employees[i].readDate(in);
		}
		return employees;
	}
}

class Employee {
	private String name;
	private double salary;
	private Date hireDay;

	public Employee() {

	}

	public Employee(String n, double s, int year, int month, int day) {
		this.name = n;
		this.salary = s;
		GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
		hireDay = calendar.getTime();
	}

	public String getName() {
		return name;
	}

	public double getSalary() {
		return salary;
	}

	public Date getHireDay() {
		return hireDay;
	}

	public void raiseSalary(double byPercent) {
		double raise = salary * byPercent / 100;
		salary += raise;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + "[name=" + name + ",salary=" + salary + ",hireDay=" + hireDay + "]";
	}

	public void writeData(PrintWriter out) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(hireDay);
		out.println(name + "|" + salary + "|" + calendar.get(Calendar.YEAR) + "|" + calendar.get(Calendar.MONTH) + 1
				+ "|" + calendar.get(Calendar.DAY_OF_MONTH));
	}

	public void readDate(Scanner in) {
		String line = in.nextLine();
		String[] tokens = line.split("\\|");
		name = tokens[0];
		salary = Double.parseDouble(tokens[1]);
		int y = Integer.parseInt(tokens[2]);
		int m = Integer.parseInt(tokens[3]);
		int d = Integer.parseInt(tokens[4]);
		GregorianCalendar calendar = new GregorianCalendar(y, m - 1, d);
		hireDay = calendar.getTime();
	}
}