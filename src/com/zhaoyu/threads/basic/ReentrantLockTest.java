package com.zhaoyu.threads.basic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock除了sychornize的功能外，还有额外的作用，重入锁会被最后一个成功取得锁，并还没有释放的线程持有，当持有锁的线程尝试lock时，会立即返回，
 * 可以通过方法isHeldByCurrentThread和getHoldCount检查当前线程是否持有锁。
 * ReentrantLock继承了Sync（有公平锁和非公平锁两个子类）,而Sync继承了{@code AbstractQueuedSynchronizer}，AbstractQueuedSynchronizer是CLH队列锁的变种，
 * 有head,tail,state和继承自{@code AbstractOwnableSynchronizer}类的exclusiveOwnerThread字段，底层使用本地方法CompareAndSet和volatile
 * 实现了线程安全。CAS操作会比较期待的旧值是否和内存中的一样（底层通过内存地址比较），如果一致，将旧值改变为新值。
 *
 * ReentrantLock使用AbstractQueuedSynchronizer的state字段记录重入的次数，实现可重入性。
 *
 * ReentrantLock是一种自旋锁（spinLock）， spinLock会出现
 * 1：ABA问题，通过添加版本号解决，如1A-2B-3C。
 * 2：指令会一直会尝试比较，直到超时为止（一般会设定一个尝试次数），所以会引入循环比较的开销。
 * 3：只能保证一个共享变量的原子操作，对于多个共享变量，CAS无法保证其原子性，对于引用变量，可以使用AtomaticReference
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
