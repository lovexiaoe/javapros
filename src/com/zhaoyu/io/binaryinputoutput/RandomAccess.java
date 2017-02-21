package com.zhaoyu.io.binaryinputoutput;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * RandomAccessFile��������ļ��е��κ�λ�ò��һ�д�����ݡ������ļ�����������ʵģ����Ǵ����������������ȴ���С�����Դ�һ��
 * ��������ļ���ֻ���ڶ������ͬʱ���ڶ�д�����ǿ���ͨ��ʹ�á�r�� ��rw����Ϊ�������Ĳ���ָ�����ѡ�
 *
 * ʹ��RandomAccessFile��Employee��������ļ���д��
 *
 * @author xiaoE
 */
public class RandomAccess {
	public static void main(String[] args) {
		Employee[] staff = new Employee[3];
		staff[0] = new Employee("��2", 75000, 1984, 12, 14);
		staff[1] = new Employee("����", 50000, 1988, 5, 4);
		staff[2] = new Employee("��6", 35000, 1990, 6, 13);
		try {
			// д���Ա��¼
			DataOutputStream out = new DataOutputStream(new FileOutputStream("employee2.dat"));
			for (Employee employee : staff) {
				employee.writeData(out);
			}
			out.close();

			// RandomAccessFileʵ�ַ����ȡstaff���飬����һ���µĹ�Ա���顣
			RandomAccessFile in = new RandomAccessFile("employee2.dat", "r");
			int n = (int) (in.length() / Employee.RECORD_SIZE);
			Employee[] newStaffs = new Employee[n];
			for (int i = n - 1; i >= 0; i--) {
				newStaffs[i] = new Employee();
				in.seek(i * Employee.RECORD_SIZE);
				System.out.println(in.getFilePointer());
				newStaffs[i].readDate(in);
			}
			in.close();

			// ��ӡ�µĹ�Ա���顣
			for (Employee e : newStaffs) {
				System.out.println(e);
			}

		} catch (Exception e) {
		}
	}
}

class Employee {
	private String name;
	private double salary;
	private Date hireDay;

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

	/**
	 * ��һ��Employee����д�����С������ַ������ݹ̶���Сд�롣
	 *
	 * @param out
	 * @throws IOException
	 */
	public void writeData(DataOutput out) throws IOException {
		DataIO.writeFixedString(name, NAME_SIZE, out);
		out.writeDouble(salary);

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(hireDay);
		out.writeInt(calendar.get(Calendar.YEAR));
		out.writeInt(calendar.get(Calendar.MONTH + 1));
		out.writeInt(calendar.get(Calendar.DAY_OF_MONTH));

	}

	/**
	 * �����ж�ȡEmployee����
	 *
	 * @param in
	 * @throws IOException
	 */
	public void readDate(DataInput in) throws IOException {
		name = DataIO.readFixedString(NAME_SIZE, in);
		salary = in.readDouble();
		int y = in.readInt();
		int m = in.readInt();
		int d = in.readInt();
		GregorianCalendar calendar = new GregorianCalendar(y, m - 1, d);
		hireDay = calendar.getTime();
	}
}

class DataIO {
	/**
	 * �̶����ȶ�ȡ�������ȡ���ֽ�0�����ȡ�����������ȡsize���ֽڣ������ַ�����
	 *
	 * @param size
	 * @param in
	 * @return String
	 * @throws IOException
	 */
	public static String readFixedString(int size, DataInput in) throws IOException {
		StringBuilder sb = new StringBuilder(size);
		int i = 0;
		boolean more = true;
		while (more && i < size) {
			char ch = in.readChar();
			i++;
			if (ch == 0) {
				more = false;
			} else {
				sb.append(ch);
			}
		}
		in.skipBytes(2 * (size - i));
		return sb.toString();
	}

	/**
	 * д��̶����ȸ����ַ������ʵ�ʳ��Ȳ��㣬��0�ֽڲ��䡣
	 *
	 * @param s
	 * @param size
	 * @param out
	 * @throws IOException
	 */
	public static void writeFixedString(String s, int size, DataOutput out) throws IOException {
		for (int i = 0; i < size; i++) {
			char ch = 0;
			if (i < s.length()) {
				ch = s.charAt(i);
			}
			out.writeChar(ch);
		}
	}
}