package com.zhaoyu.reflect.aboutclass;

import java.util.Date;

/**
 * ������ʱ�ڼ䣬java�����˵ڸ��������������Ϣ���������Class,
 *
 * �ڳ�������ʱ����main��������ᱻ���أ����ţ���������������ϵ���μ��غܶ��࣬
 * ���ڴ��͵ĳ�����˵��ÿ��������Ҫ���Ѻܶ�ʱ�䣬
 * һ�ֽ�������ǣ���main�����ٲ�Ҫ���������࣬Ȼ������ͨ��forName�ֶ������࣬
 *
 * @author xiaoe
 *
 */
public class AboutClass {
	public static void main(String[] args) {
		Date date = new Date();
		Class c1 = date.getClass();
		// ��ȡ����������ơ�
		System.out.println(c1.getName());

		String className = "java.util.Date";
		Class c2 = null;
		try {
			// ʹ��forName��ȡ�����Class����
			// �������ֻ��className��һ������߽ӿ�ʱ����ʹ�á�������׳�һ������쳣��
			c2 = Class.forName(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// �����Ȼint����һ���࣬����int.class��һ��Class����
		Class c3 = int.class;
		// ��ӡ���Ϊ��int
		System.out.println(c3.getName());

		/*
		 * ��ʷԭ���������������һ����������֡�
		 * [Ljava.lang.Double;
		 * [I
		 */
		System.out.println(Double[].class.getName());
		System.out.println(int[].class.getName());

		// Class ��Ķ���ʹ��==�Ƚ�
		if (c2 == Date.class) {
			System.out.println("c2����ΪDate");
		}

		// ʹ��Class.newInstance����ʹ����Ĭ�ϵĹ��캯�����ٴ���һ��ʵ����
		// ���û��Ĭ�ϵĹ��캯������׳��쳣��
		try {
			System.out.println("�ַ���".getClass().newInstance() + "�õ��˿��ַ���");
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
