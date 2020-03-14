package net.zhaoyu.javapros.j2se.io.objectStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 该类使用序列化流结合clone对对象进行深层复制。实现Serializable接口的对象处理进行的是深拷贝。
 * 改变对象默认的序列化行为需要，可序列化的类可以定义下面方法实现自定义序列化。
 * private void readObject(ObjectInputStream in)throws IOException,ClassNotFoundException;
 * private void writeObject(ObjectOutputStream out)throws IOException;
 *
 * 更高级的自定义序列化方法还有readExternal和writeExternal方法。
 *
 * @author xiaoE
 *
 */

/**
 * 单例和枚举在进行序列化时，由于序列化产生一个全新的对象，在使用==检测对象的相等性时，会打破原有的这种安全机制。
 * 为了解决这一问题需要定义一个readResolve的特殊序列化方法。在java5后的enum结构中，jdk已经实现了这种安全机制。
 * 在实现java5以前的枚举或者 单例设计模式的类中需要实现这一特殊方法。
 */

/**
 * 序列化时，虚拟机必须要了解每个对象的结构，所以有些慢，如果关心性能问题，并且要读写某个特定类的大量对象，需要研究Externalizable接口。
 * 
 * @author xiaoE
 *
 */

public class SerialCloneTest {
	public static void main(String[] args) {
		Employee harry = new Employee("亨利", 35000, 1989, 10, 1);
		Employee harry2 = (Employee) harry.clone();
		harry.raiseSalary(10);

		System.out.println(harry);
		System.out.println(harry2);
	}
}

/**
 * 该类利用对象序列化实现了对象的克隆。
 * 对象输出输入流的使用需要实现Serializable接口
 *
 * @author xiaoE
 *
 */
class SerialCloneable implements Cloneable, Serializable {
	@Override
	public Object clone() {
		try {
			// 将对象写入字节数组缓存，
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			// 对象输出流
			ObjectOutputStream out = new ObjectOutputStream(bout);
			out.writeObject(this);
			out.close();

			// 从字节数组缓存读取对象，返回。
			ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
			// 对象输入流
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
	// transiend用于阻止序列化对字段的读写。下面hireDay在序列化后为null。
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
