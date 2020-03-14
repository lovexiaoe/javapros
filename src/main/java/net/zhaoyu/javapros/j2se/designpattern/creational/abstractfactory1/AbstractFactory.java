package net.zhaoyu.javapros.j2se.designpattern.creational.abstractfactory1;

public abstract class AbstractFactory {
	abstract Color getColor(String color);

	abstract Shape getShape(String shape);
}
