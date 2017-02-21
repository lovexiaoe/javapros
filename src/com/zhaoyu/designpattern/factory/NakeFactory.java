package com.zhaoyu.designpattern.factory;

public class NakeFactory implements Factory {

	@Override
	public Shoe generateShoe() {
		return new NakeShoe();
	}

}
