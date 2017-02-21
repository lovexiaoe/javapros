package com.zhaoyu.threads.conditionlock_producer_consumer;

import java.util.Random;

/**
 * �����߶Ի��������ж�ȡ
 * 
 * @author xiaoe
 * 
 */
public class Consumer implements Runnable {
	private final static Random generator = new Random();
	private final Buffer sharedLocation;

	public Consumer(Buffer sharedLocation) {
		this.sharedLocation = sharedLocation;
	}

	public void run() {
		int sum = 0;

		for (int count = 1; count < 10; count++) {
			try {
				Thread.sleep(generator.nextInt(3000));
				sum += sharedLocation.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.printf("\n%s %d\n%s\n", "�����߶�ȡ����", sum, "�����߶�ȡ���");
	}
}
