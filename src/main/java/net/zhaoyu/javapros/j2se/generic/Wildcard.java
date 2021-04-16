package net.zhaoyu.javapros.j2se.generic;

import java.util.ArrayList;

/**
 * 通配符处理
 *
 * 带有超类型限定的通配符可以向泛型对象写入，带有子类型限定的通配符可以从泛型对象读取。
 *
 * @author xiaoE
 *
 */
public class Wildcard {
	public static void main(String[] args) {
		// 带有超类型限定的通配符
		// 在下面名中使用<? extends Employee>才能对Employee及子类进行操作。
		ArrayList<?> wildList;
		ArrayList<Manager> maList = new ArrayList<Manager>();
		ArrayList<? extends Employee> emList = maList; // ok
		// wildList.add(new Manager()); // error

	}
}

class Employee {

}

class Manager extends Employee {
	// 带有子类型限定的通配符
	public static ArrayList<? super Manager> getsub(Manager[] a, ArrayList<? super Manager> elist) {
		for (int i = 0; i < a.length; i++) {
			if (i < 2) {
				elist.add(a[i]);
			}
		}
		return elist;
	}
}

/**
 * Comparable接口本身就是一个泛型类型，
 * 可以把数组中取得最小值的方法声明 为
 * public static <T extends Comparable<T>> T min(T[] a)
 *
 * <T extends Comparable<? super T>>的解释。
 * GregorianCalendar 是Calendar的子类，并且Calendar实现了Comparable<Calendar>。因此GregorianCalendar
 * 实现的是Comparable<Calendar>,而不是Comparable<GregorianCalendar>,在这种情况下，当处理一个
 * GregorianCalendar对象的数组时，应该将上面方法写成：
 * public static <T extends Comparable<? super T>> T min(T[] a)
 * compareTo方法写成：
 * int compareTo(? super T)
 */

