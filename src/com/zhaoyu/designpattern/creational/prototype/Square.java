package com.zhaoyu.designpattern.creational.prototype;

public class Square extends Shape {
	public Square() {
		type = "Square";
	}

	@Override
	public void draw() {
		System.out.println("Draw a Square.");
	}
}
