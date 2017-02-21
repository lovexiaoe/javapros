package com.zhaoyu.threads.basic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁除了sychornize的功能外，还有额外的作用，重入锁会被最后一个成功取得锁，并还没有释放的线程持有，当持有锁的线程尝试lock时，会立即返回，
 * 可以通过方法isHeldByCurrentThread和getHoldCount检查当前线程是否持有锁。
 * 
 * @author dmall223
 *
 */
public class ReentrantLockTest {
	public static void main(String[] args) {
		Resource resource = new Resource();
		UpdateResource ur1 = new UpdateResource(resource);
		Thread t1 = new Thread(ur1);
		// 子线程调用addI。
		t1.start();
		// 主线程持续读8次，共4秒。
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

	// 写操作加重入锁。
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

	// 读取没有加锁
	public int getI() {
		return i;
	}
}
