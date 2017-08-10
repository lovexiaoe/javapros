package com.zhaoyu.designpattern.creational.builder;

public class ChickenBurger extends Burger {
	@Override
	public float price() {
		return 40.5f;
	}

	@Override
	public String name() {
		return "Chicken Burger";
	}
}
