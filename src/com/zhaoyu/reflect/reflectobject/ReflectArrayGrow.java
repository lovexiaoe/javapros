package com.zhaoyu.reflect.reflectobject;

import java.lang.reflect.Array;

/**
 * 该类对任意的数组进行扩展，拓展规则是增加原来的10分之1的大小 ，由于考虑到数组较短时，增加10分之1太小，所以再加10
 *
 * java数组会记住每个元素的类型，即创建时new表达式中的元素类型。将一个Employee[]临时转换成Object[]数组，然后再把
 * 它转换回来是可以的，但是一个从开始就是Object[]的数组却永远不能转换成Employee[]。
 *
 * 由于在扩展时数组类型的不同，扩展时需要根据不同 的类型进行扩展。
 *
 * @author xiaoe
 *
 */
public class ReflectArrayGrow {
	public static void main(String[] args) {

	}

	// 这个方法是不能用于扩展其它类型的数组的，它是对Object类型的数组进行扩展。
	static Object[] badArrayGrow(Object[] a) {
		int newLength = a.length * 11 / 10 + 10;
		Object[] newArray = new Object[newLength];
		System.arraycopy(a, 0, newArray, 0, a.length);
		return newArray;
	}

	// 此方法根据反射原理获取了原数组的元素类型，根据元素类型扩展数组并返回。适合于各种类型的数组。
	static Object goodArrayGrow(Object[] a) {
		Class c1 = a.getClass();
		if (!c1.isArray()) {
			return null;
		}
		Class componentType = c1.getComponentType();
		int length = Array.getLength(a);
		int newLength = length * 11 / 10 + 10;
		Object newArray = Array.newInstance(componentType, newLength);
		System.arraycopy(a, 0, newArray, 0, newLength);
		return newArray;
	}

}
