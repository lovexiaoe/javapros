package com.zhaoyu.designpattern.singleton;


/**
 * 使用内部类也可以达到延迟加载单例的目的，且对线程友好。 InnerSingleton
 * 在加载时，内部类不会被初始化，所以确保InnerSingleton被载入JVM时，不会初始化instance。
 * 而instance的创建是在类加载时完成的，所以天生对多线程友好。
 * 
 * @author dmall223
 *
 */
public class InnerSingleton {
	private InnerSingleton() {
		System.out.println("InnerSingleton is created!");
	}

	private static class SingletonHolder {
		private static InnerSingleton instance = new InnerSingleton();
	}

	public static InnerSingleton getInstance() {
		return SingletonHolder.instance;
	}

	public static void main(String[] args) {
		getInstance();
		getInstance();

	}
}
