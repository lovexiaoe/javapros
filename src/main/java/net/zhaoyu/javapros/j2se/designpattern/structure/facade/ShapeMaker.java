package net.zhaoyu.javapros.j2se.designpattern.structure.facade;

/**
 * 提供内部系统的黑箱操作，通过模板和客户端交互，屏蔽内部复杂的结构。Mybatis的SqlSession接口也使用了门面模式。
 * 大部分和Mybatis的交互都使用SqlSession完成。
 */
public class ShapeMaker {
	private Shape circle;
	private Shape rectangle;
	private Shape square;

	public ShapeMaker() {
		circle = new Circle();
		rectangle = new Rectangle();
		square = new Square();
	}

	public void drawCircle() {
		circle.draw();
	}

	public void drawRectangle() {
		rectangle.draw();
	}

	public void drawSquare() {
		square.draw();
	}
}
