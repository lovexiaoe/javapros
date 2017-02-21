package com.zhaoyu.reflect.reflectobject;

import java.lang.reflect.Method;

/**
 * java语言中并没有像c++语言中的那样拥有方法指针，即将一个方法的地址传递给别一个方法，方便别一个方法进行调用 。
 * 但是我们可以通过反射的invoke调用另一个方法。即Method.invoke方法。
 * 这种传递方法地址的方式还可以采用接口回调实现，反射的方式不常用。
 *
 * 本例子通过反射调用square和sqrt方法，分别打印出两组表。
 *
 * @author xiaoe
 *
 */
public class MethodInvoke {
	public static void main(String[] args) throws SecurityException, NoSuchMethodException {
		// 获取Method
		// square必须是public，因为Class.getMethod不属于这个包。
		Method square = MethodInvoke.class.getMethod("square", double.class);
		Method sqrt = Math.class.getMethod("sqrt", double.class);

		printTable(1, 10, 10, square);
		printTable(1, 10, 10, sqrt);
	}

	/**
	 * 返回平方数
	 *
	 * @param x
	 * @return
	 */
	public static double square(double x) {
		return x * x;
	}

	// 根据传入的参数和方法参数 ，利用反射执行方法。
	static void printTable(double from, double to, int n, Method f) {
		// 打印方法名称
		System.out.println(f);

		double dx = (to - from) / (n - 1);
		for (double x = from; x <= to; x += dx) {
			try {
				double y = (Double) f.invoke(null, x);
				System.out.printf("%10.4f|%10.4f%n", x, y);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
