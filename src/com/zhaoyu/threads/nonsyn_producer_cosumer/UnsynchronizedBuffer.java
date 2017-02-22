package com.zhaoyu.threads.nonsyn_producer_cosumer;

/**
 * 共享缓冲区，生产者进行写入，消费者进行读取
 * @author xiaoe
 *
 */
public class UnsynchronizedBuffer implements Buffer{

	//这里为了简单，定义一个长度为1的缓冲区，供producer和cosumer写读。
	private int buffer=-1;
	@Override
	public int get() throws InterruptedException {
		System.out.printf("消费者读取了\t%2d",buffer);
		return buffer;
	}

	@Override
	public void set(int value) throws InterruptedException {
		System.out.printf("生产者写入了\t%2d",value);
		buffer=value;
	}

}
