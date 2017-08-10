package com.zhaoyu.designpattern.structure.facade;

public class Rectangle implements Shape {

	@Override
	public void draw() {
		System.out.println("Shape: Rectangle!");
	}
}
