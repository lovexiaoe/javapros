package com.zhaoyu.threads.arrayblockingqueue_producer_cosumer;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * �û�����ʹ����ͬ����ArrayBlockingQueue��ArrayBlockingQueue��һ��ȫ��ʵ����BlockingQueue�ӿڵġ�
 * �̰߳�ȫ�Ļ������ࡣArrayBlockingQueue���������ݱ�����һ�������У�������鴴��ʱ�涨��С��
 * �����󲻻�����չ��
 * ���������������߽���д�룬�����߽��ж�ȡ
 *
 * @author xiaoe
 *
 */
public class synchronizedBuffer implements Buffer {

	private final ArrayBlockingQueue<Integer> buffer;

	public synchronizedBuffer(int value) {
		buffer = new ArrayBlockingQueue<Integer>(value);
	}

	@Override
	public int get() throws InterruptedException {
		int readValue = buffer.take();
		System.out.printf("�����߶�ȡ��\t%2d", readValue);
		return readValue;
	}

	@Override
	public void set(int value) throws InterruptedException {
		System.out.printf("������д����\t%2d", value);
		buffer.put(value);
	}

}
