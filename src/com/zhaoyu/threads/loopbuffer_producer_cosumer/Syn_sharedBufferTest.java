package com.zhaoyu.threads.loopbuffer_producer_cosumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Syn_sharedBufferTest {
	
	/**
	 * ���������̷ֱ߳���������ߺ�������
	 * @param args
	 */
	public static void main(String[] args) {
		ExecutorService application=Executors.newCachedThreadPool();
		
		Buffer sharedLocation=new synchronizedBuffer();
		
		application.execute(new Producer(sharedLocation));
		application.execute(new Consumer(sharedLocation));
		application.shutdown();
	}

}
