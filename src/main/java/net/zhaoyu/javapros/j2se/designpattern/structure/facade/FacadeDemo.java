package net.zhaoyu.javapros.j2se.designpattern.structure.facade;

public class FacadeDemo {
	public static void main(String[] args) {
		ShapeMaker shapeMaker = new ShapeMaker();

		shapeMaker.drawCircle();
		shapeMaker.drawRectangle();
		shapeMaker.drawSquare();
	}
}
