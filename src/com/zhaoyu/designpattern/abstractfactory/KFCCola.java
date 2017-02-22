package com.zhaoyu.designpattern.abstractfactory;

/**
 * @author 肯德基的可乐
 * 
 */
public class KFCCola implements Cola {
	public void drink() {
		System.out.println("Drink KFCCola");
	}

	void doSomething() {
		System.out.println("做其它事情！");
	}
}
