package net.zhaoyu.javapros.j2se.designpattern.structure.Decorator;

public class DecoratorDemo {
	public static void main(String[] args) {
		Shape circle = new Circle();

		Shape redCircle = new RedShapeDecorator(new Circle());

		Shape redRectangle = new RedShapeDecorator(new Rectangle());
		System.out.println("circle with nomal border");
		circle.draw();
		System.out.println("\ncircle with red border");
		redCircle.draw();
		System.out.println("\nrectangle with red border");
		redRectangle.draw();
	}
}
