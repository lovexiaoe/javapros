package net.zhaoyu.javapros.j2se.designpattern.structure.Decorator;

public class RedShapeDecorator extends ShapeDecrator {

	public RedShapeDecorator(Shape shape) {
		super(shape);
	}

	@Override
	public void draw() {
		decoratorShap.draw();
		setRedBorder(decoratorShap);
	}

	private void setRedBorder(Shape decoratorShap) {
		System.out.println("Border color: Red");
	}
}
