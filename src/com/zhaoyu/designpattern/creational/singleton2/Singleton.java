package com.zhaoyu.designpattern.creational.singleton2;

//懒加载模式，用到才会被加载。
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

	//第二种模式，使用内部类实现单例模式,类加载机制保证了线程互斥性，并且类只有被用到的时候才会被加载，所以保证了延迟加载。
	public static Singleton getSingleTon2(){
		return SingletonInstance.instance;
	}
	private static class SingletonInstance{
		private static final Singleton  instance=new Singleton("利用类加载创建的一单例");
	}
}


