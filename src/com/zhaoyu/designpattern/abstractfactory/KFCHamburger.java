package com.zhaoyu.designpattern.abstractfactory;

/**
 * 肯德基的汉堡包
 * 
 * @author zhaoyu
 * 
 */
public class KFCHamburger implements Hamburger {
	public void eat() {
		System.out.println("eat KFCHamburger!");
	}

	void foo() {
		System.out.println("this is foo");
	}
}
