package com.zhaoyu.designpattern.factory;

/**
 * author 大神
 */
public class NakeFactory implements Factory {

	@Override
	public Shoe generateShoe() {
		return new NakeShoe();
	}

}
