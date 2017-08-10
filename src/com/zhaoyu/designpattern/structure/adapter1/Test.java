package com.zhaoyu.designpattern.structure.adapter1;

public class Test {

	public static void main(String[] args) {
		// ������
		Target target1 = new Adapter1();
		target1.adapteeMethod();
		target1.adapterMethod();

		// ��������
		Adaptee adaptee = new Adaptee();
		Target target = new Adapter(adaptee);
		target.adapteeMethod();
		target.adapterMethod();
	}
}
