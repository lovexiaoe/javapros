package com.zhaoyu.designpattern.structure.adapter1;

public class Test {

	public static void main(String[] args) {
		// ÀàÊÊÅä
		Target target1 = new Adapter1();
		target1.adapteeMethod();
		target1.adapterMethod();

		// ¶ÔÏóÊÊÅä
		Adaptee adaptee = new Adaptee();
		Target target = new Adapter(adaptee);
		target.adapteeMethod();
		target.adapterMethod();
	}
}
