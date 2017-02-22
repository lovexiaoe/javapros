package com.zhaoyu.designpattern.factory;

/**
 * author 多点
 */
public class AddidasFactory implements Factory {

	@Override
	public Shoe generateShoe() {
		return new AddidasShoe();
	}

}
