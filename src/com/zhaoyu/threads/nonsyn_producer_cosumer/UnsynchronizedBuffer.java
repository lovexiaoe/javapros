package com.zhaoyu.threads.nonsyn_producer_cosumer;

/**
 * ���������������߽���д�룬�����߽��ж�ȡ
 * @author xiaoe
 *
 */
public class UnsynchronizedBuffer implements Buffer{

	//����Ϊ�˼򵥣�����һ������Ϊ1�Ļ���������producer��cosumerд����
	private int buffer=-1;
	@Override
	public int get() throws InterruptedException {
		System.out.printf("�����߶�ȡ��\t%2d",buffer);
		return buffer;
	}

	@Override
	public void set(int value) throws InterruptedException {
		System.out.printf("������д����\t%2d",value);
		buffer=value;
	}

}
