package com.zhaoyu.designpattern.abstractfactory;

/**
 * Âóµ±ÀÍ
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
