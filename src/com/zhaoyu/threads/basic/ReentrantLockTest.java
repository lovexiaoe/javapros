package com.zhaoyu.threads.basic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ����������sychornize�Ĺ����⣬���ж�������ã��������ᱻ���һ���ɹ�ȡ����������û���ͷŵ��̳߳��У������������̳߳���lockʱ�����������أ�
 * ����ͨ������isHeldByCurrentThread��getHoldCount��鵱ǰ�߳��Ƿ��������
 * 
 * @author dmall223
 *
 */
public class ReentrantLockTest {
	public static void main(String[] args) {
		Resource resource = new Resource();
		UpdateResource ur1 = new UpdateResource(resource);
		Thread t1 = new Thread(ur1);
		// ���̵߳���addI��
		t1.start();
		// ���̳߳�����8�Σ���4�롣
		for (int i = 0; i < 8; i++) {
			System.out.println(resource.getI());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

class UpdateResource implements Runnable {
	Resource resource;

	public UpdateResource(Resource locktest) {
		this.resource = locktest;
	}

	public void run() {
		resource.addI();
	}
}

class Resource {

	private Integer i = 12;

	private Lock lock = new ReentrantLock();

	// д��������������
	public int addI() {
		try {
			if (lock.tryLock(100, TimeUnit.MILLISECONDS)) {
				Thread.sleep(3000);
				i = i + 12;
			} else {
				throw new UnsupportedOperationException();
			}
		} catch (Exception e) {
		} finally {
			lock.unlock();
		}
		return i;
	}

	// ��ȡû�м���
	public int getI() {
		return i;
	}
}
