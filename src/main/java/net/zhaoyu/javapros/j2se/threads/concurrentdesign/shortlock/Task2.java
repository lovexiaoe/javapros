package net.zhaoyu.javapros.j2se.threads.concurrentdesign.shortlock;

import java.util.concurrent.locks.Lock;

/**
 * 小范围锁，性能佳。
 */
public class Task2 implements Runnable {

	private final Lock lock;
	
	public Task2 (Lock lock) {
		this.lock=lock;
	}
	
	@Override
	public void run() {
		lock.lock();
		Operations.readData();
		lock.unlock();
		Operations.processData();
		lock.lock();
		Operations.writeData();
		lock.unlock();
	}
}
