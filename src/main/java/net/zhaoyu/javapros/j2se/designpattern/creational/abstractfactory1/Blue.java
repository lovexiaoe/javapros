package net.zhaoyu.javapros.j2se.designpattern.creational.abstractfactory1;

public class Blue implements Color {
	@Override
	public void fill() {
		System.out.println("Inside Blue::fill() method.");
	}
}
