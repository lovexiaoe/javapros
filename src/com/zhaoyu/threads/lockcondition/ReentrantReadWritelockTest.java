package com.zhaoyu.threads.lockcondition;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 用于理解ReentrantReadWriteLock的使用。 适用于很多线程从一个数据结构读取数据而很少线程修改其中的数据
 * 。在这种情况下允许读线程共享访问。写线程互斥访问。
 * 
 * 读取 没有线程正在做写操作，且没有线程在请求写操作。 写入 没有线程正在做读写操作。
 *
 * @author xiaoE
 *
 */
public class ReentrantReadWritelockTest {
	public static void main(String[] args) {
		TrylockClazz1 clazz1 = new TrylockClazz1();
		ThreadRunTest3 r2 = new ThreadRunTest3(clazz1);
		Thread t2 = new Thread(r2);
		t2.start();

		ThreadRunTest3 r3 = new ThreadRunTest3(clazz1);
		Thread t3 = new Thread(r3);
		t3.start();
	}
}

class ThreadRunTest3 implements Runnable {
	TrylockClazz1 clazz;

	public ThreadRunTest3(TrylockClazz1 clazz) {
		this.clazz = clazz;
	}

	@Override
	public void run() {
		System.out.println("线程：" + Thread.currentThread().getId() + " 的i加1再*2开始，i为：" + clazz.getI());
		clazz.addI();
		System.out.println("线程：" + Thread.currentThread().getId() + "测试end! i为：" + clazz.getI());
	}

}

class TrylockClazz1 {
	private int i = 0;
	// 重入读写锁的使用
	ReadWriteLock iLock = new ReentrantReadWriteLock();
	private Lock readLock = iLock.readLock();
	private Lock writeLock = iLock.writeLock();

	public int addI() {
		try {
			if (writeLock.tryLock(100, TimeUnit.MILLISECONDS)) {
				i++;
				i = i * 2;
			} else {
				throw new UnsupportedOperationException();
			}

		} catch (Exception e) {
		} finally {
			writeLock.unlock();
		}
		return i;
	}

	public String getI() {
		String iStr = "";
		try {
			readLock.tryLock();
			iStr = iStr + i;
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			readLock.unlock();
		}
		return iStr;
	}
}