package com.zhaoyu.generic;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Scanner;

/**
 * ������еķ���������Ϣ
 * �������д��룺
 * public static <T extends Comparable<? super T>> T min(T[] a)
 * ���Ͳ��������£�
 * public static Comparable min(Comparable[] a)
 * �������Ͳ�����Ȼ����һЩ���͵ļ��䡣 ����ʹ��javase5�ṩ�ķ���API��ȷ����
 * 1�����������һ��T�Ĳ�������
 * 2�����T�����Ͳ�����һ���������޶�������������һ���������͡�
 * 3������޶�������һ��ͨ���������
 * 4�����ͨ���������һ���������޶���
 * 5��������ͷ�����һ���������������
 *
 * Ϊ�˱�ﷺ�����͵�������javaSE5��reflect�����ṩ��һ���µĽӿ�Type������ӿڰ������������ͣ�
 * 1��Class�࣬�����������͡�
 * 2��TypeVariable�ӿڣ��������ͱ������� T extends Comparable<? super T>,E����
 * 3��WildcardType�ӿڣ�����ͨ�������? super T,?����
 * 4��ParameterizedType�ӿڣ������������ӿ����ͣ���Comparable<? super T>,Collection<E>�ȣ���
 * 5��GenericArrayType�ӿڣ������������飨��T[]����
 *
 * �������÷��ͷ���API��ӡ����Ķ���ͷ�����
 *
 * @author xiaoE
 *
 */
public class GenericAndReflect {
	public static void main(String[] args) {
		String name;
		if (args.length > 0) {
			name = args[0];
		} else {
			Scanner in = new Scanner(System.in);
			System.out.println("����������ƣ���java.util.Collections:");
			name = in.next();
		}

		try {
			Class c1 = Class.forName(name);
			printClass(c1);
			for (Method m : c1.getDeclaredMethods()) {
				printMethod(m);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ӡ�����ƣ�����ӡ��extends �� implements�����ݡ�
	 *
	 * @param c1
	 */
	public static void printClass(Class c1) {
		System.out.print(c1);
		// ��ӡ���������ͱ�������List<E>�е�<E>��
		printTypes(c1.getTypeParameters(), "<", ", ", ">", true);
		Type sc = c1.getGenericSuperclass();
		if (sc != null) {
			System.out.print(" extends ");
			printType(sc, false);
		}
		printTypes(c1.getGenericInterfaces(), " implements ", ", ", "", false);
		System.out.println();
	}

	// ��ӡ������
	public static void printMethod(Method m) {
		String name = m.getName();
		System.out.print(Modifier.toString(m.getModifiers()));
		System.out.print(" ");
		printTypes(m.getTypeParameters(), "<", ",", ">", true);

		printType(m.getGenericReturnType(), false);
		System.out.print(" ");
		System.out.print(name);
		System.out.print("(");
		printTypes(m.getGenericParameterTypes(), "", ", ", "", false);
		System.out.println(")");
	}

	/**
	 * ��ӡ���Ͳ����б�
	 *
	 * @param types
	 * @param pre
	 * @param sep
	 * @param suf
	 * @param isDefinition
	 */
	public static void printTypes(Type[] types, String pre, String sep, String suf, boolean isDefinition) {
		// �̳���Object�����Ͳ���ӡ
		if (pre.equals(" extends ") && Arrays.equals(types, new Type[] { Object.class })) {
			return;
		}
		if (types.length > 0) {
			System.out.print(pre);
		}
		for (int i = 0; i < types.length; i++) {
			if (i > 0) {
				System.out.print(sep);
			}
			printType(types[i], isDefinition);
		}
		if (types.length > 0) {
			System.out.print(suf);
		}
	}

	/**
	 * ��һ�����ͽ��д�ӡ��������Ϳ�����5�������� ��
	 *
	 * @param type
	 * @param isDefinition
	 *            ���ͱ����Ƿ��޶���
	 */
	public static void printType(Type type, boolean isDefinition) {
		if (type instanceof Class) {
			Class t = (Class) type;
			System.out.print(t.getName());
		} else if (type instanceof TypeVariable) {
			// �������͵Ĵ�ӡ<T>,<T extends Number>
			TypeVariable t = (TypeVariable) type;
			System.out.print(t.getName());
			if (isDefinition) {
				printTypes(t.getBounds(), " extends ", " & ", "", false);
			}
		} else if (type instanceof WildcardType) {
			// ͨ�����ӡ��? extends Number
			WildcardType t = (WildcardType) type;
			System.out.print("?");
			printTypes(t.getUpperBounds(), " extends ", " & ", "", false);
			printTypes(t.getLowerBounds(), " super ", " & ", "", false);
		} else if (type instanceof ParameterizedType) {
			// ���������͵Ĵ�ӡ����Collection<E>
			ParameterizedType t = (ParameterizedType) type;
			// getOwnerType if this type is O<T>.I<S>, return a representation of O<T>.
			Type owner = t.getOwnerType();
			if (owner != null) {
				printType(owner, false);
				System.out.print(".");
			}
			printType(t.getRawType(), false);
			printTypes(t.getActualTypeArguments(), "<", ", ", ">", false);
		} else if (type instanceof GenericArrayType) {
			// �����������͵Ĵ�ӡ����T[]
			GenericArrayType t = (GenericArrayType) type;
			System.out.print("");
			printType(t.getGenericComponentType(), isDefinition);
			System.out.print("[]");
		}
	}

}
