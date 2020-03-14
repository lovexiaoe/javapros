package net.zhaoyu.javapros.j2se.designpattern.creational.singleton1;


//直接创建实例，如果这个类没有用到也会加载这个实例。
public class SingleObject {
	// create an object of SingleObject
	private static SingleObject instance = new SingleObject();

	// make the constructor private so that this class cannot be
	// instantiated
	private SingleObject() {
	}

	// Get the only object available
	public static SingleObject getInstance() {
		return instance;
	}

	public void showMessage() {
		System.out.println("Hello World!");
	}
}
