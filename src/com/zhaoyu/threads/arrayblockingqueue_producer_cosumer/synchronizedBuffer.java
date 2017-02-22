package com.zhaoyu.threads.arrayblockingqueue_producer_cosumer;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 该缓冲区使用了同步的ArrayBlockingQueue。ArrayBlockingQueue是一个全面实现了BlockingQueue接口的、
 * 线程安全的缓冲区类。ArrayBlockingQueue将共享数据保存在一个数组中，这个数组创建时规定大小，
 * 创建后不会再扩展。
 * 共享缓冲区，生产者进行写入，消费者进行读取
 *
 * @author xiaoe
 *
 */
public class synchronizedBuffer implements Buffer {

	private final ArrayBlockingQueue<Integer> buffer;

	public synchronizedBuffer(int value) {
		buffer = new ArrayBlockingQueue<Integer>(value);
	}

	@Override
	public int get() throws InterruptedException {
		int readValue = buffer.take();
		System.out.printf("消费者读取了\t%2d", readValue);
		return readValue;
	}

	@Override
	public void set(int value) throws InterruptedException {
		System.out.printf("生产者写入了\t%2d", value);
		buffer.put(value);
	}

}
