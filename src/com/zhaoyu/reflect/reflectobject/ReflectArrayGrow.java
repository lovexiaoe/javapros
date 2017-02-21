package com.zhaoyu.reflect.reflectobject;

import java.lang.reflect.Array;

/**
 * �������������������չ����չ����������ԭ����10��֮1�Ĵ�С �����ڿ��ǵ�����϶�ʱ������10��֮1̫С�������ټ�10
 *
 * java������סÿ��Ԫ�ص����ͣ�������ʱnew���ʽ�е�Ԫ�����͡���һ��Employee[]��ʱת����Object[]���飬Ȼ���ٰ�
 * ��ת�������ǿ��Եģ�����һ���ӿ�ʼ����Object[]������ȴ��Զ����ת����Employee[]��
 *
 * ��������չʱ�������͵Ĳ�ͬ����չʱ��Ҫ���ݲ�ͬ �����ͽ�����չ��
 *
 * @author xiaoe
 *
 */
public class ReflectArrayGrow {
	public static void main(String[] args) {

	}

	// ��������ǲ���������չ�������͵�����ģ����Ƕ�Object���͵����������չ��
	static Object[] badArrayGrow(Object[] a) {
		int newLength = a.length * 11 / 10 + 10;
		Object[] newArray = new Object[newLength];
		System.arraycopy(a, 0, newArray, 0, a.length);
		return newArray;
	}

	// �˷������ݷ���ԭ���ȡ��ԭ�����Ԫ�����ͣ�����Ԫ��������չ���鲢���ء��ʺ��ڸ������͵����顣
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
