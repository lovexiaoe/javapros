package net.zhaoyu.javapros.j2se.designpattern.creational.abstractfactory1;

public class Circle implements Shape {
	@Override
	public void draw() {
		System.out.println("Inside Circle::draw() method.");
	}
}
