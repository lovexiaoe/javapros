package net.zhaoyu.javapros.j2se.io.binaryData;

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
 * RandomAccessFile类可以在文件中的任何位置查找或写入数据。磁盘文件都是随机访问的，但是从网络而来的数据流却不行。你可以打开一个
 * 随机访问文件，只用于读入或者同时用于读写，我们可以通过使用”r” 或”rw”作为构造器的参数指定这个选项。
 *
 * 该例使用DataOutputStream写入文件，然后使用RandomAccessFile对文件倒序读取。
 *
 * @author xiaoE
 */
public class RandomAccess {
	public static void main(String[] args) {
		Employee[] staff = new Employee[3];
		staff[0] = new Employee("赵2", 75000, 1984, 12, 14);
		staff[1] = new Employee("王八", 50000, 1988, 5, 4);
		staff[2] = new Employee("刘6", 35000, 1990, 6, 13);
		try {
			// 写入雇员记录
			DataOutputStream out = new DataOutputStream(new FileOutputStream("employee2.dat"));
			for (Employee employee : staff) {
				employee.writeData(out);
			}
			out.close();

			// RandomAccessFile实现反向读取staff数组，生成一个新的雇员数组。
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

			// 打印新的雇员数组。
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
	 * 将一个Employee对象写入流中。名称字符串根据固定大小写入。
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
	 * 从流中读取Employee对象，
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
	 * 固定长度读取，如果读取到字节0，则读取结束，否则读取size个字节，返回字符串。用于读取文件中Employee的name,name的长度不确定。
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
	 * 写入固定长度个的字符，如果实际长度不足，以0字节补充。，用于写入Employee的name,name的长度不确定。
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