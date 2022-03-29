package net.zhaoyu.javapros.j2se.designpattern.creational.singleton2;

//懒汉模式，延迟加载，用到才会被加载，解决了饿汉模式的缺点。
public final class SingletonSafe {
	private static volatile SingletonSafe singleton;
	String s;

	private SingletonSafe(String s) {
		this.s = s;
	}

	public String getS() {
		return s;
	}

	public void setS(String ss) {
		s = ss;
	}

	/**
	 * JVM在创建对象时，大概分为如下三个步骤。
	 * 1.分配内存空间。
	 * 2.实例化一个对象。
	 * 3.将实例化对象分为给变量。
	 * 但是CPU在执行过程中可能会对cpu指令进行重排（参考线程安全有序性），导致可能是132的执行步骤。
	 * 那么在synchronized内部，在极高并发的场景下，会出现将13执行了，2还没有执行的情况，这时，另一个线程发现变量不为空，
	 * 那么直接拿去使用，但是此时变量应用的是一个空对象，会报错。
	 * 解决方法就是使用volatile关键字，禁止cpu指令重排序。
	 */
	public static SingletonSafe getSingleTon() {
		// 双重锁+volatile，保证线程安全初始化。
		// 这个模式将同步内容下方到if内部，提高了执行的效率，不必每次获取对象时都进行同步，只有第一次才同步，创建了以后就没必要了。
		if (singleton == null) {
			synchronized (SingletonSafe.class) {
				if (singleton == null) {
					singleton = new SingletonSafe("我只有一个实例");
				}
			}
		}
		return singleton;
	}
}


