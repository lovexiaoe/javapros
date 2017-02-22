package com.zhaoyu.designpattern.abstractfactory;

/**
 * 肯德基
 * 
 * @author zhaoyu
 * 
 */
public class KFC implements AbstractFactory {

	@Override
	public Cola genCola() {
		return new KFCCola();
	}

	@Override
	public Hamburger genHamburger() {
		return new KFCHamburger();
	}

}
