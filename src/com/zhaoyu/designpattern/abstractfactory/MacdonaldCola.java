package com.zhaoyu.designpattern.abstractfactory;

/**
 * 麦当劳的可乐
 * 
 * @author zhaoyu
 * 
 */
public class MacdonaldCola implements Cola {
	public void drink() {
		System.out.println("drink MacdonaldCola!");
	}

	void doSomeThing() {
		System.out.println("做其它事情！");
	}
}
