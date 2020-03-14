package net.zhaoyu.javapros.j2se.designpattern.structure.facade;

public class Square implements Shape {

	@Override
	public void draw() {
		System.out.println("Shape: Square!");
	}
}
