package net.zhaoyu.javapros.j2se.designpattern.creational.factory1;


/**
 * 工厂模式，使用工厂构建某个类的实例。好处：对象的创建统一到一处。
 * 使用案例：
 * mybatis中的DefaultSqlSessionFactory
 */
public class FactoryPatternDemo {
	public static void main(String[] args) {
		ShapeFactory shapeFactory = new ShapeFactory();

		// get an object of Circle and call its draw method.
		Shape shape1 = shapeFactory.getShape("CIRCLE");

		// call draw method of Circle
		shape1.draw();

		// get an object of Rectangle and call its draw method.
		Shape shape2 = shapeFactory.getShape("RECTANGLE");

		// call draw method of Rectangle
		shape2.draw();

		// get an object of Square and call its draw method.
		Shape shape3 = shapeFactory.getShape("SQUARE");

		// call draw method of circle
		shape3.draw();
	}
}
// 输出
/*
Inside Circle::draw() method.
Inside Rectangle::draw() method.
Inside Square::draw() method.
*/
