package net.zhaoyu.javapros.j2se.designpattern.creational.singleton1;


// 懒汉模式，该模式是线程安全的，懒得思考，在类初始化的时候先创建再说，
// （这个类没有用，造成资源的浪费）问题不予考虑。
// （程序加载慢）问题也不予考虑。
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
