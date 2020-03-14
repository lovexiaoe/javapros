package net.zhaoyu.javapros.j2se.threads.ProducerConsumer.loopbuffer_producer_cosumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Syn_sharedBufferTest {
	
	/**
	 * 启用两个线程分别调用生产者和消费者
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
