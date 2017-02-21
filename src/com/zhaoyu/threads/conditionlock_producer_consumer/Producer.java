package com.zhaoyu.threads.conditionlock_producer_consumer;

import java.util.Random;

/**
 * 生产者对缓冲区进行写入
 * 
 * @author xiaoe
 * 
 */
public class Producer implements Runnable {
	private final static Random generator = new Random();
	private final Buffer sharedLocation;

	public Producer(Buffer sharedLocation) {
		this.sharedLocation = sharedLocation;
	}

	public void run() {
		int sum = 0;

		for (int count = 1; count < 10; count++) {
			try {
				Thread.sleep(generator.nextInt(3000));
				sharedLocation.set(count);
				sum += count;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("生产者完成生产");
	}
}
