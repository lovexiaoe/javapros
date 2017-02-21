package com.zhaoyu.threads.conditionlock_producer_consumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ʹ��condition+lock���ͬ�����ʹ������ݡ�
 *
 * @author xiaoe
 *
 */
public class synchronizedBuffer implements Buffer {

	// ��������
	private final Lock accessLock = new ReentrantLock();

	// ������ƶ�д��conditions
	private final Condition canWrite = accessLock.newCondition();
	private final Condition canRead = accessLock.newCondition();

	private int buffer = -1;
	private boolean occupied = false;

	@Override
	public void set(int value) throws InterruptedException {
		// lock this object �����synchronizedBuffer�������
		accessLock.lock();
		try {
			while (occupied) {
				System.out.print("�����߳���д��");
				displayState("Buffer full.�����ߵȴ���");
				// ������wait()���ȴ�ֱ��occupiedΪ�ա�
				canWrite.await();
			}
			buffer = value;
			occupied = true;
			displayState("������д�룺" + buffer);
			// ֪ͨ�ȴ���buffer��ȡ���κ��̡߳�������notifyAll();
			canRead.signalAll();
		} finally {
			// unlock this object
			// ��ʹ�������쳣��unlock����ִ�У�����ᷢ��������
			accessLock.unlock();
		}
	}

	@Override
	public int get() throws InterruptedException {
		int readValue = 0;
		// lock this object ���synchronizedBuffer�������
		accessLock.lock();
		try {
			while (!occupied) {
				System.out.println("�����߳��Զ�ȡ��");
				displayState("Buffer empty,�����ߵȴ���");
				// �ȴ�ֱ��buffer��Ϊ�ա�
				canRead.await();
			}

			occupied = false;
			readValue = buffer;
			displayState("�����߶�ȡ��" + readValue);
			// ֪ͨ�ȴ���bufferд����κ��̡߳�������notifyAll();
			canWrite.signalAll();
		} finally {
			// ��ʹ�������쳣��unlock����ִ�У�����ᷢ��������
			accessLock.unlock();
		}
		return readValue;
	}

	public void displayState(String operation) {
		System.out.printf("%-40s%d\t\t%b\n\n", operation, buffer, occupied);
	}
}
