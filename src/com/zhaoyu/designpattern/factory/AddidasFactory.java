package com.zhaoyu.designpattern.factory;

public class AddidasFactory implements Factory {

	@Override
	public Shoe generateShoe() {
		return new AddidasShoe();
	}

}
