package net.zhaoyu.javapros.j2se.designpattern.structure.facade;

/**
 * 将不同组件提供的各个功能封装，提供一个整体操作的入口。可以操作这些所有的功能。
 * 主要目的是封装，屏蔽内部的细节。如mybatis的sqlSession接口。
 */
public class FacadeDemo {
	public static void main(String[] args) {
		ShapeMaker shapeMaker = new ShapeMaker();

		shapeMaker.drawCircle();
		shapeMaker.drawRectangle();
		shapeMaker.drawSquare();
	}
}
