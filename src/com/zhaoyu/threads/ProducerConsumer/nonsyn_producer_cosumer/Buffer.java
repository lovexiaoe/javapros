package com.zhaoyu.threads.ProducerConsumer.nonsyn_producer_cosumer;


/**
 * 共享区的接口，生产者需要在共享区写入，消费者需要在共享区读取。
 * @author xiaoe
 *
 */
public interface Buffer {
	public int get() throws InterruptedException;
	public void set(int value) throws InterruptedException;
}
