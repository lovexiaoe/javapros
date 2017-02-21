package com.zhaoyu.reflect.reflectobject;

import java.lang.reflect.Method;

/**
 * java�����в�û����c++�����е�����ӵ�з���ָ�룬����һ�������ĵ�ַ���ݸ���һ�������������һ���������е��� ��
 * �������ǿ���ͨ�������invoke������һ����������Method.invoke������
 * ���ִ��ݷ�����ַ�ķ�ʽ�����Բ��ýӿڻص�ʵ�֣�����ķ�ʽ�����á�
 *
 * ������ͨ���������square��sqrt�������ֱ��ӡ�������
 *
 * @author xiaoe
 *
 */
public class MethodInvoke {
	public static void main(String[] args) throws SecurityException, NoSuchMethodException {
		// ��ȡMethod
		// square������public����ΪClass.getMethod�������������
		Method square = MethodInvoke.class.getMethod("square", double.class);
		Method sqrt = Math.class.getMethod("sqrt", double.class);

		printTable(1, 10, 10, square);
		printTable(1, 10, 10, sqrt);
	}

	/**
	 * ����ƽ����
	 *
	 * @param x
	 * @return
	 */
	public static double square(double x) {
		return x * x;
	}

	// ���ݴ���Ĳ����ͷ������� �����÷���ִ�з�����
	static void printTable(double from, double to, int n, Method f) {
		// ��ӡ��������
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
