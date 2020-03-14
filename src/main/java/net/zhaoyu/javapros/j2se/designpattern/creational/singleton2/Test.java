package net.zhaoyu.javapros.j2se.designpattern.creational.singleton2;

public class Test {
	public static void main(String[] args) {
		Singleton singleton1 = Singleton.getSingleTon();
		System.out.println(singleton1.getS());
	}
}
