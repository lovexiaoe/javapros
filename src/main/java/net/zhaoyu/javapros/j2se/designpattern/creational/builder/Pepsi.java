package net.zhaoyu.javapros.j2se.designpattern.creational.builder;

public class Pepsi extends ColdDrink {

	@Override
	public String name() {
		return "Coke";
	}

	@Override
	public float price() {
		return 30.0f;
	}

}
