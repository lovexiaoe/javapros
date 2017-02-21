package com.zhaoyu.threads.automatic;

public class TestLongAutomatic {
	public static void main(String[] args) {
		final P1 v = new P1();

		// �߳� 1������ b = 0
		final Thread t1 = new Thread() {
			public void run() {
				while (true) {
					v.set1();
				}
			};
		};
		t1.start();

		// �߳� 2������ b = -1
		final Thread t2 = new Thread() {
			public void run() {
				while (true) {
					v.set2();
				}
			};
		};
		t2.start();

		// �߳� 3����� 0 != b && -1 != b
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
		// if�ж��в���һ��ԭ�Ӳ����������ڶ��߳��£��ᵼ��if����������ӡ������
		if (0 != b && -1 != b) {
			System.out.println(b + "Error");
		}
	}
}