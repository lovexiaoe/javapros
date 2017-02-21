package com.zhaoyu.reflect.reflectclass;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * ������Ҫ���ַ��������Ϣ�Ĳ�����
 *
 * ��reflect������������Field,Method��Constructor�ֱ�������������ֶΣ������͹�������
 *
 * Class���е�getFields��getMethods��getConstructors�����ֱ𷵻����ṩ��public�ֶΡ������͹��������顣
 * ���а�������Ľ��г�Ա��
 *
 * Class���е�getDeclaredFields��getDeclaredMethods��getDeclaredConstructors�����ֱ𷵻�����������ȫ���򡢷�����
 * �����������а�����private��protected��Ա�����ǲ���������ĳ�Ա ��
 *
 * ��������ӡһ�����ȫ����Ϣ��
 *
 * @author xiaoe
 *
 */
public class AnalyseClass {
	public static void main(String[] args) {
		String name = "java.lang.Double";
		try {
			Class c1 = Class.forName(name);
			Class superc1 = c1.getSuperclass();
			String modifiers = Modifier.toString(c1.getModifiers());
			if (modifiers.length() > 0) {
				System.err.print(modifiers + " ");
			}
			System.out.print("class " + name);
			if (superc1 != null && superc1 != Object.class) {
				System.out.print(" extends " + superc1.getName());
			}
			System.out.print("\n{\n");
			printConstructors(c1);
			System.out.println();
			printMethods(c1);
			System.out.println();
			printMethods(c1);
			System.out.println("}");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * ��ӡһ��������й��캯����
	 *
	 */
	static void printConstructors(Class<?> clazz) {
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		for (Constructor<?> constructor : constructors) {
			String name = constructor.getName();
			System.out.print("	");
			// ͨ��Modifier��ȡ�ʹ�ӡ���η���
			String modifiers = Modifier.toString(constructor.getModifiers());
			if (modifiers.length() > 0) {
				System.out.print(modifiers + " ");
			}
			// ��ӡ����
			System.out.print(name + "(");

			// ��ӡ��������
			Class[] paramTypes = constructor.getParameterTypes();
			for (int i = 0; i < paramTypes.length; i++) {
				if (i > 0) {
					System.out.print(", ");
				}
				System.out.print(paramTypes[i].getName());
			}
			System.out.println(");");
		}
	}

	/**
	 * ��ӡһ��������з�����
	 *
	 * @param clazz
	 */
	static void printMethods(Class<?> clazz) {
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {

			Class retType = method.getReturnType();

			String name = method.getName();
			System.out.print("	");
			// ��ӡmodifier
			String modifiers = Modifier.toString(method.getModifiers());
			if (modifiers.length() > 0) {
				System.out.print(modifiers + " ");
			}

			// ��ӡ����
			System.out.print(retType.getName() + " " + name + "(");

			// ��ӡ����
			Class<?>[] paramTypes = method.getParameterTypes();
			for (int i = 0; i < paramTypes.length; i++) {
				if (i > 0) {
					System.out.print(", ");
				}
				System.out.print(paramTypes[i].getName());
			}
			System.out.println(");");
		}
	}

	static void printFields(Class<?> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Class<?> type = field.getType();
			String name = field.getName();
			System.out.print("	");
			String modifiers = Modifier.toString(field.getModifiers());
			if (modifiers.length() > 0) {
				System.out.print(modifiers + " ");
			}
			System.out.println(type.getName() + " " + name + ";");
		}
	}
}
