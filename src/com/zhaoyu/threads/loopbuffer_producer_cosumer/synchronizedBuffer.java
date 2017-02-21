package com.zhaoyu.threads.loopbuffer_producer_cosumer;

/**
 * ���������Լ�ͨ��wait��notify��nofityAll��ʵ��ѭ�����������߳�ͬ�� ����������������������������д�룬��������ǰ����ж�ȡ��
 * ���������ڶ��е����ݽṹΪѭ����������ɵ�ѭ�����У������߰���˳��д�������� ����˳�����������Ϊ0ʱ�������߶����������ݡ�
 * 
 * @author xiaoe
 * 
 */
public class synchronizedBuffer implements Buffer {

	private final int[] buffer = { -1, -1, -1 };

	// ������������
	private int count;
	// ��һ��д��Ԫ�ص�index
	private int writeIndex = 0;
	// ��һ����ȡԪ�ص�index
	private int readIndex = 0;

	@Override
	public synchronized int get() throws InterruptedException {
		while (count == 0) {
			// System.out.println("���л������ǿ��ˣ�");
			wait();
		}

		int readValue = buffer[readIndex];
		System.out.println("��������������  " + readIndex + " ��  " + readValue);
		count--;
		readIndex = (readIndex + 1) % buffer.length;
		notifyAll();
		return readValue;
	}

	@Override
	public synchronized void set(int value) throws InterruptedException {
		while (count == buffer.length) {
			// System.out.println("���л�����д����");
			wait();
		}
		buffer[writeIndex] = value;
		System.out.println("��������  " + writeIndex + " д��  " + value);
		count++;
		writeIndex = (writeIndex + 1) % buffer.length;
		notifyAll();
	}

}
