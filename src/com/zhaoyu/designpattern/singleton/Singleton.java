package com.zhaoyu.designpattern.singleton;

public final class Singleton {
	// 这里使用的是延迟加载，即在getSingleTon方法调用时加载事例。
	private static Singleton singleton;
	String s;

	private Singleton(String s) {
		this.s = s;
	}

	public String getS() {
		return s;
	}

	public void setS(String ss) {
		s = ss;
	}

	public static Singleton getSingleTon() {
		// 双重锁的形式，保证线程安全初始。
		// 这个模式将同步内容下方到if内部，提高了执行的效率，不必每次获取对象时都进行同步，只有第一次才同步，创建了以后就没必要了。
		if (singleton == null) {
			synchronized (Singleton.class) {
				if (singleton == null) {
					singleton = new Singleton("我只有一个实例");
				}
			}
		}
		return singleton;
	}

	public static void main(String[] args) {
		Singleton singleton1 = Singleton.getSingleTon();
		System.out.println(singleton1.getS());
	}
}
