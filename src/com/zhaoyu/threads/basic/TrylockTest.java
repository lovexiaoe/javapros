package com.zhaoyu.threads.basic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * �������trylock����ʱ��
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
		System.out.println("�̣߳�" + Thread.currentThread().getId() + " ��i��1��*2��ʼ��iΪ��" + clazz.getI());
		clazz.addI();
		System.out.println("�̣߳�" + Thread.currentThread().getId() + "����end! iΪ��" + clazz.getI());
	}

}

class TrylockClazz {
	private int i = 0;
	Lock iLock = new ReentrantLock();

	public int addI() {
		try {
			// �������볬ʱ��
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