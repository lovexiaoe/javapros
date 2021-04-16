package net.zhaoyu.javapros.j2se.basic.oop.objectdetail;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 此类根据manager继承了employee的关系重写了equal,hashCode,toString方法。
 *
 * @author xiaoe
 *
 */
public class ObjectOverwrite {

}

class Employee {
	private String name;
	private double salary;
	private Date date;

	// constructor
	public Employee(String name, double salary, int year, int month, int day) {
		this.name = name;
		this.salary = salary;
		GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
		this.date = calendar.getTime();
	}

	// getters and setters
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	// 重写equals
	@Override
	public boolean equals(Object otherObject) {
		if (this == otherObject) {
			return true;
		}
		if (otherObject == null) {
			return false;
		}
		if (this.getClass() != otherObject.getClass()) {
			return false;
		}

		Employee other = (Employee) otherObject;
		return name.equals(other.getName()) && salary == other.getSalary() && date.equals(other.getDate());
	}

	// 重写hashCode
	@Override
	public int hashCode() {
		return 7 * name.hashCode() + 11 * new Double(salary).hashCode() + 13 * date.hashCode();
	}

	// 重写toString
	@Override
	public String toString() {
		return this.getClass().getName() + "[name=" + name + ",salary=" + salary + ",date=" + date + "]";
	}
}

class Manager extends Employee {
	private double bonus;

	public double getBonus() {
		return bonus;
	}

	public void setBonus(double bonus) {
		this.bonus = bonus;
	}

	public Manager(String n, double s, int year, int month, int day) {
		super(n, s, year, month, day);
		this.bonus = 0;
	}

	// 根据父类重写equals
	@Override
	public boolean equals(Object otherObject) {
		if (!super.equals(otherObject)) {
			return false;
		}
		// super.equals已经使用getClass检测了otherObject和this属于同一个类，所以可以直接转换。
		Manager other = (Manager) otherObject;
		return bonus == other.bonus;
	}

	// 根据父类重写hashCode
	@Override
	public int hashCode() {
		return super.hashCode() + 17 * new Double(bonus).hashCode();
	}

	// 重写toString
	@Override
	public String toString() {
		return super.toString() + "[bonus=" + bonus + "]";
	}
}