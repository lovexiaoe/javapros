package com.zhaoyu.threads.conditionlock_producer_consumer;


/**
 * �������Ľӿڣ���������Ҫ�ڹ�����д�룬��������Ҫ�ڹ�������ȡ��
 * @author xiaoe
 *
 */
public interface Buffer {
	public int get() throws InterruptedException;
	public void set(int value) throws InterruptedException;
}
