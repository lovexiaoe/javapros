package net.zhaoyu.javapros.j2se.threads.concurrentdesign.shortlock;

import java.util.concurrent.locks.Lock;


/**
 * 大范围锁。性能不佳。
 */
public class Task1 implements Runnable {

	private final Lock lock;
	
	public Task1 (Lock lock) {
		this.lock=lock;
	}
	
	@Override
	public void run() {
		lock.lock();
		Operations.readData();
		Operations.processData();
		Operations.writeData();
		lock.unlock();
	}

}
