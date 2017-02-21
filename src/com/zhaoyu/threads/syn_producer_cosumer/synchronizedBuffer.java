package com.zhaoyu.threads.syn_producer_cosumer;

/**
 * ���������Լ�ͨ��wait��notify��nofityAll��ʵ���н绺�������߳�ͬ��
 * ����������������������������д�룬��������ǰ����ж�ȡ��
 * ���������ڶ��е����ݽṹΪ��ѭ����������ɵĶ��У�������ֻ���ڸ�λд����������
 * ֻ���ڵ�λ��������дһ���ҵ������λʱ��λΪ���λ��
 * @author xiaoe
 *
 */
public class synchronizedBuffer implements Buffer{

	private final int[] buffer={-1,-1,-1};
	
	//��һ��д��Ԫ�ص�index
	private int writeIndex=0;
	//��һ����ȡԪ�ص�index
	private int readIndex=0;

	@Override
	public synchronized int get() throws InterruptedException {
		while (readIndex==writeIndex) {
			//System.out.println("���л������ǿ��ˣ�");
			if (readIndex==buffer.length) {
				//�����������������Ԫ��ʱ�����ö�д��������notify�����ߡ�
				readIndex=writeIndex=0;
				notifyAll();
			}else {
				wait();
			}
			
		}
		
		int readValue=buffer[readIndex];
		System.out.println("��������������  "+readIndex+" ��  "+readValue);

		readIndex++;
		notifyAll();
		return readValue;
	}

	@Override
	public synchronized void set(int value) throws InterruptedException {
		while (writeIndex>=buffer.length) {
			//System.out.println("���л�����д����");
			wait();
		}
		buffer[writeIndex]=value;
		System.out.println("��������  "+writeIndex+" д��  "+value);
		writeIndex++;
		notifyAll();
	}
	
}
