package com.zhaoyu.designpattern.abstractfactory;

/**
 * ���͵Ŀ���
 * 
 * @author zhaoyu
 * 
 */
public class MacdonaldCola implements Cola {
	public void drink() {
		System.out.println("drink MacdonaldCola!");
	}

	void doSomeThing() {
		System.out.println("���������飡");
	}
}
