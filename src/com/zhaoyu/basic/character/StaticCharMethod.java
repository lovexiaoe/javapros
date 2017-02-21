package com.zhaoyu.basic.character;

public class StaticCharMethod {
	public static void main(String[] args) {

		// char c = 'A'; // get input character
		char c = '��';

		// display character info
		// �ж�c�Ƿ���Unicode�ַ����С�
		System.out.printf("is defined: %b\n", Character.isDefined(c));
		// �Ƿ�����
		System.out.printf("is digit: %b\n", Character.isDigit(c));
		// �Ƿ�����Ϊjava��ʶ���еĵ�һ����ĸ��
		System.out.printf("is first character in a Java identifier: %b\n", Character.isJavaIdentifierStart(c));
		// �Ƿ��Ǳ�ʶ����һ���֡�
		System.out.printf("is part of a Java identifier: %b\n", Character.isJavaIdentifierPart(c));
		// �Ƿ�Ϊ��ĸ
		System.out.printf("is letter: %b\n", Character.isLetter(c));
		// �Ƿ���ĸ������
		System.out.printf("is letter or digit: %b\n", Character.isLetterOrDigit(c));
		// �Ƿ�Сд��ĸ
		System.out.printf("is lower case: %b\n", Character.isLowerCase(c));
		// �Ƿ��д��ĸ
		System.out.printf("is upper case: %b\n", Character.isUpperCase(c));
		// ת��Ϊ��д
		System.out.printf("to upper case: %s\n", Character.toUpperCase(c));
		// ת��ΪСд
		System.out.printf("to lower case: %s\n", Character.toLowerCase(c));
	}
}
