package net.zhaoyu.javapros.j2se.designpattern.creational.builder;

public class Coke extends ColdDrink {

	@Override
	public String name() {
		return "Pepsi";
	}

	@Override
	public float price() {
		return 35.0f;
	}

}
