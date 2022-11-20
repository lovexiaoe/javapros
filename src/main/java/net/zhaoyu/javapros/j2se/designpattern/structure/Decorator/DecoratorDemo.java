package net.zhaoyu.javapros.j2se.designpattern.structure.Decorator;


/**
 * 在现有功能的基础上，不改变其内部，增加一个额外的功能。如画图形，你想给画图增加颜色，可以使用装饰器模式。
 * 和适配器模式的区别，适配器不会增加额外的能力，只是将现有的多个功能统一。
 */
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
