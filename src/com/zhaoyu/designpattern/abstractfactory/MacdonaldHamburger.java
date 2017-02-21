package com.zhaoyu.designpattern.abstractfactory;

/**
 * 麦当劳的汉堡包
 * 
 * @author zhaoyu
 * 
 */
public class MacdonaldHamburger implements Hamburger {
	public void eat() {
		System.out.println("eat Macdonald hamburger!");
	}

	void doSomeThing() {
		System.out.println("做其它事情！");
	}
}
