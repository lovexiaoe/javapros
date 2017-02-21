package com.zhaoyu.threads.conditionlock_producer_consumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Syn_sharedBufferTest {

	/**
	 * ���������̷ֱ߳���������ߺ�������
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ExecutorService application = Executors.newCachedThreadPool();

		Buffer sharedLocation = new synchronizedBuffer();

		System.out.printf("%-40s%s\t\t%s\n%-40s%s\n\n", "Operation", "Buffer",
				"Occupied", "----------", "------\t\t--------");

		application.execute(new Producer(sharedLocation));
		application.execute(new Consumer(sharedLocation));
		application.shutdown();
	}

}
