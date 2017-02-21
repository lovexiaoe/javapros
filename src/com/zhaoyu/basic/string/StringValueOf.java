package com.zhaoyu.basic.string;

/*
 * java�е�ÿ��Object��������һ��toString�������������ַ����������ڻ������͡�
 * String�ṩ��valueOf���������κ����͵Ĳ���ת��Ϊһ��String����
 */
public class StringValueOf {
	public static void main(String[] args) {
		char[] charArray = { 'a', 'b', 'c', 'd', 'e', 'f' };
		boolean booleanValue = true;
		char characterValue = 'Z';
		int integerValue = 7;
		long longValue = 10000000000L; // L suffix indicates long
		float floatValue = 2.5f; // f indicates that 2.5 is a float
		double doubleValue = 33.333; // no suffix, double is default
		Object objectRef = "hello"; // assign string to an Object reference

		// ��һ������ת�����ַ�����
		System.out.printf("char array = %s\n", String.valueOf(charArray));
		// �������һ����ת��Ϊ�ַ�����
		System.out.printf("part of char array = %s\n", String.valueOf(charArray, 3, 3));
		// boolean��char,...ת��Ϊ�ַ�����
		System.out.printf("boolean = %s\n", String.valueOf(booleanValue));
		System.out.printf("char = %s\n", String.valueOf(characterValue));
		System.out.printf("int = %s\n", String.valueOf(integerValue));
		System.out.printf("long = %s\n", String.valueOf(longValue));
		System.out.printf("float = %s\n", String.valueOf(floatValue));
		System.out.printf("double = %s\n", String.valueOf(doubleValue));
		// Object������toString����������תΪ���崮��
		System.out.printf("Object = %s\n", String.valueOf(objectRef));
	}
}
