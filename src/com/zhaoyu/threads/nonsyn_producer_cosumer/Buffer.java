package com.zhaoyu.threads.nonsyn_producer_cosumer;


/**
 * �������Ľӿڣ���������Ҫ�ڹ�����д�룬��������Ҫ�ڹ�������ȡ��
 * @author xiaoe
 *
 */
public interface Buffer {
	public int get() throws InterruptedException;
	public void set(int value) throws InterruptedException;
}
