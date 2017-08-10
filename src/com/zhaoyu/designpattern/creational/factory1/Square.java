package com.zhaoyu.designpattern.creational.factory1;

public class Square implements Shape {
	@Override
	public void draw() {
		System.out.println("Inside Square::draw() method.");
	}
}
