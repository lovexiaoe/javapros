package com.zhaoyu.oop.classdetail;

/**
 * is-a����һ��������Ϊ���û����򡱣������������г��ָ��������κεط� ������������������û���
 * 
 * @author xiaoe
 *
 */
public class Polymorphism {
	public static void main(String[] args) {
		Son son = new Son();
		Polymorphism[] fathers = new Polymorphism[3];
		fathers[0] = son;
		// ����ʧ�ܣ�fathers[0]��������father,���ܵ���son�ķ�����Ӧ����son�����á�
		// fathers[0].sonFoo();
		son.sonFoo();

		// ��̬�����֣�һ��PolyMorphism�Ķ����ڱ����ڲ�ͬ���������ʱ������ֳ���ͬ����Ϊ��
		Polymorphism father = new Son();
		father.foo();
	}

	int i = 1;

	void foo() {
		System.out.println("do someThings!");
	}
}

class Son extends Polymorphism {
	@Override
	void foo() {
		System.out.println("son do someThing!");
	}

	void sonFoo() {
		System.out.println("son's method!");
	}
}
