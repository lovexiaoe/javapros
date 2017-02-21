package com.zhaoyu.threads.basic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//��д����ʹ�ã���ʾһ��д��������3�룬�����Ķ���������д�Ĺ����У���ȡ���������������м䷵��0��

/**
 * ReadWriteLock�ӿڣ���û��д������£�readLock����ͬʱ�������ȡ�߹���ʹ�ã�д���������ġ�
 * һ��ReadWriteLock�ӿڵ�ʵ���߱��뱣֤���ж�ȡ�߿����������һ���޸ĺ�����ݡ�
 * 
 * @author dmall223
 *
 */
public class ReadWriteLockTest {
	public static void main(String[] args) {
		Resource1 resource = new Resource1();
		UpdateResource1 ur1 = new UpdateResource1(resource);
		Thread t1 = new Thread(ur1);
		// ���̵߳���addI��
		t1.start();
		// ���̳߳�����8�Σ���4�롣
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
		// ��д��������ʱ��������ȡ������
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