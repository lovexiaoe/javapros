package com.zhaoyu.designpattern.structure.adapter;

public class Test {

	public static void main(String[] args) {
		// 类适配
		Target target1 = new Adapter1();
		target1.adapteeMethod();
		target1.adapterMethod();

		// 对象适配
		Adaptee adaptee = new Adaptee();
		Target target = new Adapter(adaptee);
		target.adapteeMethod();
		target.adapterMethod();
	}
}
