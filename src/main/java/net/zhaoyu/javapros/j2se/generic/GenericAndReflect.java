package net.zhaoyu.javapros.j2se.generic;

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
 * 虚拟机中的泛型类型信息
 * 对于下列代码：
 * public static <T extends Comparable<? super T>> T min(T[] a)
 * 泛型擦除后如下：
 * public static Comparable min(Comparable[] a)
 * 但是类型擦除仍然保留一些泛型的记忆。 可以使用javase5提供的反射API来确定泛型：<T extends Comparable<? super T>>
 *
 * 为了表达泛型类型的声明，javaSE5在reflect包中提供了一个新的接口Type。这个接口包含下列子类型：
 * 1，Class类，描述具体类型。
 * 2，TypeVariable接口，描述类型变量（如 T extends Comparable<? super T>,E）。
 * 3，WildcardType接口，描述通配符（如? super T,?）。
 * 4，ParameterizedType接口，描述泛型类或接口类型（如Comparable<? super T>,Collection<E>等）。
 * 5，GenericArrayType接口，描述泛型数组（如T[]）。
 *
 * 此类利用泛型反射API打印出类的定义和方法。
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
			System.out.println("输入类的名称，如java.consumers.Collections:");
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
	 * 打印类名称，并打印出extends 和 implements的内容。
	 *
	 * @param c1
	 */
	public static void printClass(Class c1) {
		System.out.print(c1);
		// 打印这个类的类型变量。如List<E>中的<E>。
		printTypes(c1.getTypeParameters(), "<", ", ", ">", true);
		Type sc = c1.getGenericSuperclass();
		if (sc != null) {
			System.out.print(" extends ");
			printType(sc, false);
		}
		printTypes(c1.getGenericInterfaces(), " implements ", ", ", "", false);
		System.out.println();
	}

	// 打印方法。
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
	 * 打印类型参数列表。
	 *
	 * @param types
	 * @param pre
	 * @param sep
	 * @param suf
	 * @param isDefinition
	 */
	public static void printTypes(Type[] types, String pre, String sep, String suf, boolean isDefinition) {
		// 继承自Object的类型不打印
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
	 * 对一个类型进行打印，这个类型可以是5种子类型 。
	 *
	 * @param type
	 * @param isDefinition
	 *            类型变量是否限定。
	 */
	public static void printType(Type type, boolean isDefinition) {
		if (type instanceof Class) {
			Class t = (Class) type;
			System.out.print(t.getName());
		} else if (type instanceof TypeVariable) {
			// 泛型类型的打印<T>,<T extends Number>
			TypeVariable t = (TypeVariable) type;
			System.out.print(t.getName());
			if (isDefinition) {
				printTypes(t.getBounds(), " extends ", " & ", "", false);
			}
		} else if (type instanceof WildcardType) {
			// 通配符打印，? extends Number
			WildcardType t = (WildcardType) type;
			System.out.print("?");
			printTypes(t.getUpperBounds(), " extends ", " & ", "", false);
			printTypes(t.getLowerBounds(), " super ", " & ", "", false);
		} else if (type instanceof ParameterizedType) {
			// 参数化类型的打印，如Collection<E>
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
			// 泛型数组类型的打印，如T[]
			GenericArrayType t = (GenericArrayType) type;
			System.out.print("");
			printType(t.getGenericComponentType(), isDefinition);
			System.out.print("[]");
		}
	}

}
