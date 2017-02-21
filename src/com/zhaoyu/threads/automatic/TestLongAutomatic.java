package com.zhaoyu.threads.automatic;

public class TestLongAutomatic {
	public static void main(String[] args) {
		final P1 v = new P1();

		// 线程 1：设置 b = 0
		final Thread t1 = new Thread() {
			public void run() {
				while (true) {
					v.set1();
				}
			};
		};
		t1.start();

		// 线程 2：设置 b = -1
		final Thread t2 = new Thread() {
			public void run() {
				while (true) {
					v.set2();
				}
			};
		};
		t2.start();

		// 线程 3：检查 0 != b && -1 != b
		final Thread t3 = new Thread() {
			public void run() {
				while (true) {
					v.check();
				}
			};
		};
		t3.start();
	}
}

class P1 {

	private long b = 0;

	public void set1() {
		b = 0;
	}

	public void set2() {
		b = -1;
	}

	public void check() {
		// if判断中不是一个原子操作，所以在多线程下，会导致if成立，而打印出错误。
		if (0 != b && -1 != b) {
			System.out.println(b + "Error");
		}
	}
}