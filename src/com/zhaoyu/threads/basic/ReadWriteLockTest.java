package com.zhaoyu.threads.basic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//读写锁的使用，演示一个写操作持续3秒，连续的读操作。在写的过程中，获取不到读锁，所以中间返回0。

/**
 * ReadWriteLock接口，在没有写的情况下，readLock可以同时被多个读取者共享使用，写锁是排他的。
 * 一个ReadWriteLock接口的实现者必须保证所有读取者看到的是最后一次修改后的数据。
 * 
 * @author dmall223
 *
 */
public class ReadWriteLockTest {
	public static void main(String[] args) {
		Resource1 resource = new Resource1();
		UpdateResource1 ur1 = new UpdateResource1(resource);
		Thread t1 = new Thread(ur1);
		// 子线程调用addI。
		t1.start();
		// 主线程持续读8次，共4秒。
		for (int i = 0; i < 8; i++) {
			System.out.println(resource.getI());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class UpdateResource1 implements Runnable {
	Resource1 resource;

	public UpdateResource1(Resource1 locktest) {
		this.resource = locktest;
	}

	public void run() {
		resource.addI();
	}
}

class Resource1 {

	private Integer i = 12;

	ReadWriteLock iLock = new ReentrantReadWriteLock();
	private Lock readLock = iLock.readLock();
	private Lock writeLock = iLock.writeLock();

	public int addI() {
		try {
			if (writeLock.tryLock(100, TimeUnit.MILLISECONDS)) {
				Thread.sleep(3000);
				i = i + 12;
			} else {
				throw new UnsupportedOperationException();
			}
		} catch (Exception e) {
		} finally {
			writeLock.unlock();
		}
		return i;
	}

	public int getI() {
		// 在写锁被锁定时，读锁获取不到。
		if (readLock.tryLock()) {
			try {
				return i;
			} finally {
				readLock.unlock();
			}
		}
		return 0;
	}
}