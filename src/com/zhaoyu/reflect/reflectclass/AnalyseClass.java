package com.zhaoyu.reflect.reflectclass;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 此类主要体现反射对类信息的操作。
 *
 * 在reflect包中有三个类Field,Method和Constructor分别用来描述类的字段，方法和构造器。
 *
 * Class类中的getFields、getMethods、getConstructors方法分别返回类提供的public字段、方法和构造器数组。
 * 其中包括超类的仅有成员。
 *
 * Class类中的getDeclaredFields、getDeclaredMethods、getDeclaredConstructors方法分别返回类中声明的全部域、方法、
 * 构造器。其中包括了private和protected成员，但是不包括超类的成员 。
 *
 * 下面程序打印一个类的全部信息。
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
	 * 打印一个类的所有构造函数。
	 *
	 */
	static void printConstructors(Class<?> clazz) {
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		for (Constructor<?> constructor : constructors) {
			String name = constructor.getName();
			System.out.print("	");
			// 通过Modifier获取和打印修饰符。
			String modifiers = Modifier.toString(constructor.getModifiers());
			if (modifiers.length() > 0) {
				System.out.print(modifiers + " ");
			}
			// 打印名称
			System.out.print(name + "(");

			// 打印参数类型
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
	 * 打印一个类的所有方法。
	 *
	 * @param clazz
	 */
	static void printMethods(Class<?> clazz) {
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {

			Class retType = method.getReturnType();

			String name = method.getName();
			System.out.print("	");
			// 打印modifier
			String modifiers = Modifier.toString(method.getModifiers());
			if (modifiers.length() > 0) {
				System.out.print(modifiers + " ");
			}

			// 打印名称
			System.out.print(retType.getName() + " " + name + "(");

			// 打印参数
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
