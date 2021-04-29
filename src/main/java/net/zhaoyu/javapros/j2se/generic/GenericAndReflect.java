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
 *
 * 为了表达泛型类型的声明，javaSE5在reflect包中提供了一个新的接口Type。这个接口包含下列子类型：
 * 1，Class类，描述具体类型。
 * 2，TypeVariable接口，描述类型变量，即泛型（如 T extends Comparable<? super T>,E）。
 * 3，WildcardType接口，描述通配符（如? super T,?）。
 * 4，ParameterizedType接口，描述泛型类或泛型接口（如Comparable<? super T>,Collection<E>等）。
 * 5，GenericArrayType接口，描述泛型数组（如T[]）。
 *
 * @author xiaoE
 */
public class GenericAndReflect {
	/**
	 * 运行main方法，输入本包下Test类全名，test有如下，两个方法
	 * 方法：public static <T extends Comparable<T>> void mySort1(List<T> list)
	 * 1，TypeVariable，表示类型变量，即泛型，上面方法中出现的T就是类型变量，共出现两次，getTypeParameters方法会返回方法或者类的类型变量，
	 * 2，WildcardType，描述通配符，？的表达式，上述方法中没有通配符。
	 * 3，ParameterizedType，描述泛型类或者泛型接口 ，上面的Comparable<T>,List<T>表达式。
	 * 4，GenericArrayType接口，描述泛型数组（如T[]），上述方法中没有。
	 *
	 * 方法：public static <T extends Comparable<? super T>> void mySort2(List<T> list)
	 * 1，TypeVariable，表示类型变量，即泛型，上面方法中出现的T就是类型变量，共出现两次，getTypeParameters方法会返回方法或者类的类型变量，
	 * 2，WildcardType，描述通配符，如? super T，问号存在的表达式。
	 * 3，ParameterizedType，描述泛型类或者泛型接口 ，上面的Comparable<? super T>,List<T>表达式。
	 * 4，GenericArrayType接口，描述泛型数组（如T[]），上述方法中没有。
	 */
	public static void main(String[] args) {
		String name = "";
		Scanner in = new Scanner(System.in);
		System.out.println("输入类的名称，如java.util.Comparator");
		do {
			name = in.next();
			try {
				Class c1 = Class.forName(name);
				printClass(c1);
				for (Method m : c1.getDeclaredMethods()) {
					printMethod(m);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} while (!name.equals("exit"));
	}

	/**
	 * 打印类名称，并打印出extends 和 implements的内容。
	 *
	 * @param c1
	 */
	public static void printClass(Class c1) {
		System.out.print(c1);
		// Class.getTypeParameters()获取类型变量。即申明的泛型，如List<E>中的E，Map<K,V>中的K,V。
		printTypes(c1.getTypeParameters(), "<", ", ", ">", true);
		//getGenericSuperclass获取父类
		Type sc = c1.getGenericSuperclass();
		if (sc != null) {
			System.out.print(" extends ");
			printType(sc, false);
		}
		//getGenericInterfaces获取实现的接口。
		printTypes(c1.getGenericInterfaces(), " implements ", ", ", "", false);
		System.out.println();
	}

	// 打印方法。
	public static void printMethod(Method m) {
		String name = m.getName();
		System.out.print(Modifier.toString(m.getModifiers()));
		System.out.print(" ");

		//获取泛型,Method.getTypeParameters()获取类型变量，即定义的泛型，如<T extends Comparable<T>>，<T>
		printTypes(m.getTypeParameters(), "<", ",", ">", true);


		//获取返回值
		printType(m.getGenericReturnType(), false);
		System.out.print(" ");
		System.out.print(name);
		System.out.print("(");

		//获取参数
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
				//获取泛型类型的边界，如extends Number，或者extends Comparable<T>
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
			//getRawType获取实际的原始类型，如Comparable<T>返回java.lang.Comparable。
			printType(t.getRawType(), false);
			//Type.getActualTypeArguments()获取泛型类的实际参数类型，如Comparable<T>中的T。或者Comparable<? super T>中的? super T
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
