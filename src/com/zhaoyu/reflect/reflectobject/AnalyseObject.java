package com.zhaoyu.reflect.reflectobject;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/**
 * 此类中主要是通过反射对对象进行操作。
 *
 */
public class AnalyseObject {
	private ArrayList<Object> visited = new ArrayList<Object>();

	/**
	 * 这里编写的是一个通用的ToString方法。
	 * 一层一层检索对象并toString。
	 * 使用了递归
	 *
	 * @param obj
	 * @return
	 */
	public String toString(Object obj) {
		if (null == obj) {
			return "null";
		}
		if (visited.contains(obj)) {
			return "...";
		}
		visited.add(obj);
		Class<?> c1 = obj.getClass();
		if (c1 == String.class) {
			return (String) obj;
		}
		if (c1.isArray()) {
			String r = c1.getComponentType() + "[]{";
			// 获取所有字段的名称和值。
			for (int i = 0; i < Array.getLength(obj); i++) {
				if (i > 0) {
					r += ",";
				}
				Object val = Array.get(obj, i);
				if (c1.getComponentType().isPrimitive()) {
					r += val;
				} else {
					r += toString(val);
				}
			}
			return r + "}";
		}
		String r = c1.getName();
		do {
			r += "[";
			Field[] fields = c1.getDeclaredFields();
			AccessibleObject.setAccessible(fields, true);
			for (Field f : fields) {
				if (!Modifier.isStatic(f.getModifiers())) {
					if (!r.endsWith("[")) {
						r += ",";
					}
					r += f.getName() + "=";
					try {
						Class<?> t = f.getType();
						Object val = f.get(obj);
						if (t.isPrimitive()) {
							r += val;
						} else {
							r += toString(val);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			r += "]";
			c1 = c1.getSuperclass();
		} while (c1 != null);
		return r;
	}

	public static void main(String[] args) {
		ArrayList<Integer> squares = new ArrayList<Integer>();
		for (int i = 0; i <= 5; i++) {
			squares.add(i * i);
		}
		System.out.println(new AnalyseObject().toString(squares));
	}
}
