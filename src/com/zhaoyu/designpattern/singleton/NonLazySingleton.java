package com.zhaoyu.designpattern.singleton;

public class NonLazySingleton {
	private NonLazySingleton() {
		System.out.println("Singleton is create!");
	}

	// ���ӳټ��أ����ڳ�������ʱʵ���ͻᴴ��������ӡ��:Singleton is create!
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
