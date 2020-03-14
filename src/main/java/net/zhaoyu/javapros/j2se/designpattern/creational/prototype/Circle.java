package net.zhaoyu.javapros.j2se.designpattern.creational.prototype;

public class Circle extends Shape {
	public Circle() {
		type = "Circle";
	}

	@Override
	public void draw() {
		System.out.println("Draw a Circle.");
	}
}
