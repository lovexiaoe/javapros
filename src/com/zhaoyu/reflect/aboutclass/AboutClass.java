package com.zhaoyu.reflect.aboutclass;

import java.util.Date;

/**
 * 在运行时期间，java保存了第个对象所属类的信息，这个类是Class,
 *
 * 在程序启动时，含main方法的类会被加载，接着，程序会根据依赖关系依次加载很多类，
 * 对于大型的程序来说，每次启动需要花费很多时间，
 * 一种解决方案是，在main尽量少不要引用其它类，然后，我们通过forName手动加载类，
 *
 * @author xiaoe
 *
 */
public class AboutClass {
	public static void main(String[] args) {
		Date date = new Date();
		Class c1 = date.getClass();
		// 获取对象类的名称。
		System.out.println(c1.getName());

		String className = "java.util.Date";
		Class c2 = null;
		try {
			// 使用forName获取对象的Class对象。
			// 这个方法只有className是一个类或者接口时才能使用。否则会抛出一个检测异常。
			c2 = Class.forName(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 这里，虽然int不是一个类，但是int.class是一个Class对象。
		Class c3 = int.class;
		// 打印结果为：int
		System.out.println(c3.getName());

		/*
		 * 历史原因，数组的类名称是一个很奇怪名字。
		 * [Ljava.lang.Double;
		 * [I
		 */
		System.out.println(Double[].class.getName());
		System.out.println(int[].class.getName());

		// Class 类的对象使用==比较
		if (c2 == Date.class) {
			System.out.println("c2的类为Date");
		}

		// 使用Class.newInstance可以使用类默认的构造函数快速创建一个实例。
		// 如果没有默认的构造函数则会抛出异常。
		try {
			System.out.println("字符串".getClass().newInstance() + "得到了空字符串");
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
