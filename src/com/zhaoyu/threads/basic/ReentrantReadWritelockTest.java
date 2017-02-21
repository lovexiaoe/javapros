package com.zhaoyu.threads.basic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * �������ReentrantReadWriteLock��ʹ�á� �����ںܶ��̴߳�һ�����ݽṹ��ȡ���ݶ������߳��޸����е�����
 * �������������������̹߳�����ʡ�д�̻߳�����ʡ�
 * 
 * ��ȡ û���߳�������д��������û���߳�������д������ д�� û���߳���������д������
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
		System.out.println("�̣߳�" + Thread.currentThread().getId() + " ��i��1��*2��ʼ��iΪ��" + clazz.getI());
		clazz.addI();
		System.out.println("�̣߳�" + Thread.currentThread().getId() + "����end! iΪ��" + clazz.getI());
	}

}

class TrylockClazz1 {
	private int i = 0;
	// �����д����ʹ��
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