package net.zhaoyu.javapros.j2se.designpattern.creational.prototype;

public class PrototypeDemo {
	public static void main(String[] args) {
		ShapeCache.loadCache();
		Shape cloneShape1 = ShapeCache.getShape("1");
		System.out.println("Shape: " + cloneShape1.getType());

		Shape clonedShape2 = ShapeCache.getShape("2");
		System.out.println("Shape : " + clonedShape2.getType());

		Shape clonedShape3 = ShapeCache.getShape("3");
		System.out.println("Shape : " + clonedShape3.getType());
	}
}
