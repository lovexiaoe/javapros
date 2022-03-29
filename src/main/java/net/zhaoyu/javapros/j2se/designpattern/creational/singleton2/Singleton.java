package net.zhaoyu.javapros.j2se.designpattern.creational.singleton2;

//懒汉模式，延迟加载，用到才会被加载，解决了饿汉模式的缺点。
public final class Singleton {
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

	//双重检测机制在大多数场景下不会发生问题，但是存在风险。
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

}


