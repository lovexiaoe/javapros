package net.zhaoyu.javapros.j2se.designpattern.creational.prototype;

public class Rectangle extends Shape {
	public Rectangle() {
		type = "Rectangle";
	}

	@Override
	public void draw() {
		System.out.println("Draw a Rectangle.");
	}
}
