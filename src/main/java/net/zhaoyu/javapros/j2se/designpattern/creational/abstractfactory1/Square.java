package net.zhaoyu.javapros.j2se.designpattern.creational.abstractfactory1;

public class Square implements Shape {
	@Override
	public void draw() {
		System.out.println("Inside Square::draw() method.");
	}
}
