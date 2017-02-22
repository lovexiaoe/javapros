package com.zhaoyu.threads.basic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 用于理解trylock及超时。
 *
 * @author xiaoE
 *
 */
public class TrylockTest {
	public static void main(String[] args) {
		TrylockClazz clazz1 = new TrylockClazz();
		ThreadRunTest2 r2 = new ThreadRunTest2(clazz1);
		Thread t2 = new Thread(r2);
		t2.start();

		ThreadRunTest2 r3 = new ThreadRunTest2(clazz1);
		Thread t3 = new Thread(r3);
		t3.start();
	}
}

class ThreadRunTest2 implements Runnable {
	TrylockClazz clazz;

	public ThreadRunTest2(TrylockClazz clazz) {
		this.clazz = clazz;
	}

	@Override
	public void run() {
		System.out.println("线程：" + Thread.currentThread().getId() + " 的i加1再*2开始，i为：" + clazz.getI());
		clazz.addI();
		System.out.println("线程：" + Thread.currentThread().getId() + "测试end! i为：" + clazz.getI());
	}

}

class TrylockClazz {
	private int i = 0;
	Lock iLock = new ReentrantLock();

	public int addI() {
		try {
			// 尝试锁与超时。
			if (iLock.tryLock(100, TimeUnit.MILLISECONDS)) {
				i++;
				i = i * 2;
			} else {
				throw new UnsupportedOperationException();
			}

		} catch (Exception e) {
		} finally {
			iLock.unlock();
		}
		return i;
	}

	public int getI() {
		return i;
	}
}