package com.zhaoyu.designpattern.structure.Decorator;

public abstract class ShapeDecrator implements Shape {
	protected Shape decoratorShap;

	public ShapeDecrator(Shape shape) {
		this.decoratorShap = shape;
	}

	@Override
	public void draw() {
		decoratorShap.draw();
	}
}
