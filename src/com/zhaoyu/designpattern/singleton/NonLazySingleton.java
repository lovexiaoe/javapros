package com.zhaoyu.designpattern.singleton;

public class NonLazySingleton {
	private NonLazySingleton() {
		System.out.println("Singleton is create!");
	}

	// 非延迟加载，即在程序运行时实例就会创建，并打印出:Singleton is create!
	private static NonLazySingleton string = new NonLazySingleton();

	public static NonLazySingleton getInstance() {
		return string;
	}

	public static void createSting() {
		System.out.println("NonLazySingleton return!");
	}

	public static void main(String[] args) {

	}
}
