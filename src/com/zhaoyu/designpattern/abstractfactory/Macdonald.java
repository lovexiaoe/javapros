package com.zhaoyu.designpattern.abstractfactory;

/**
 * ����
 * 
 * @author Administrator
 * 
 */
public class Macdonald implements AbstractFactory {

	@Override
	public Cola genCola() {
		return new MacdonaldCola();
	}

	@Override
	public Hamburger genHamburger() {
		return new MacdonaldHamburger();
	}

}
