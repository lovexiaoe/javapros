package com.zhaoyu.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * ����ʹ�����л������clone�Զ��������㸴�ơ�ʵ��Serializable�ӿڵĶ�������е��������
 * �ı����Ĭ�ϵ����л���Ϊ��Ҫ�������л�������Զ������淽��ʵ���Զ������л���
 * private void readObject(ObjectInputStream in)throws IOException,ClassNotFoundException;
 * private void writeObject(ObjectOutputStream out)throws IOException;
 *
 * ���߼����Զ������л���������readExternal��writeExternal������
 *
 * @author xiaoE
 *
 */

/**
 * ������ö���ڽ������л�ʱ���������л�����һ��ȫ�µĶ�����ʹ��==������������ʱ�������ԭ�е����ְ�ȫ���ơ�
 * Ϊ�˽����һ������Ҫ����һ��readResolve���������л���������java5���enum�ṹ�У�jdk�Ѿ�ʵ�������ְ�ȫ���ơ�
 * ��ʵ��java5��ǰ��ö�ٻ��� �������ģʽ��������Ҫʵ����һ���ⷽ����
 */

/**
 * ���л�ʱ�����������Ҫ�˽�ÿ������Ľṹ��������Щ������������������⣬����Ҫ��дĳ���ض���Ĵ���������Ҫ�о�Externalizable�ӿڡ�
 * 
 * @author xiaoE
 *
 */

public class SerialCloneTest {
	public static void main(String[] args) {
		Employee harry = new Employee("����", 35000, 1989, 10, 1);
		Employee harry2 = (Employee) harry.clone();
		harry.raiseSalary(10);

		System.out.println(harry);
		System.out.println(harry2);
	}
}

/**
 * �������ö������л�ʵ���˶���Ŀ�¡��
 * ���������������ʹ����Ҫʵ��Serializable�ӿ�
 *
 * @author xiaoE
 *
 */
class SerialCloneable implements Cloneable, Serializable {
	@Override
	public Object clone() {
		try {
			// ������д���ֽ����黺�棬
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			// ���������
			ObjectOutputStream out = new ObjectOutputStream(bout);
			out.writeObject(this);
			out.close();

			// ���ֽ����黺���ȡ���󣬷��ء�
			ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
			// ����������
			ObjectInputStream in = new ObjectInputStream(bin);
			Object ret = in.readObject();
			in.close();

			return ret;
		} catch (Exception e) {
			return null;
		}
	}
}

class Employee extends SerialCloneable {
	private String name;
	private double salary;
	// transiend������ֹ���л����ֶεĶ�д������hireDay�����л���Ϊnull��
	private transient Date hireDay;

	public static final int NAME_SIZE = 40;
	public static final int RECORD_SIZE = 2 * NAME_SIZE + 8 + 4 + 4 + 4;

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

}
