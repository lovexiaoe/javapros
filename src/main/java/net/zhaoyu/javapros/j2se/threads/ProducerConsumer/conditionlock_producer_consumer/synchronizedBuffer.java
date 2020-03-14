package net.zhaoyu.javapros.j2se.threads.ProducerConsumer.conditionlock_producer_consumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用condition+lock完成同步访问共享数据。
 *
 * @author xiaoe
 *
 */
public class synchronizedBuffer implements Buffer {

	// 定义锁，
	private final Lock accessLock = new ReentrantLock();

	// 定义控制读写的conditions
	private final Condition canWrite = accessLock.newCondition();
	private final Condition canRead = accessLock.newCondition();

	//定义一个int类型为buffer。和定义一个数组是同一原理
	private int buffer = -1;
	//定义buffer是否被占用。
	private boolean occupied = false;

	@Override
	public void set(int value) throws InterruptedException {
		// lock this object ，获得synchronizedBuffer对象的锁
		accessLock.lock();
		try {
			while (occupied) {
				System.out.print("生产者尝试写入");
				displayState("Buffer full.生产者等待！");
				// 类似于wait()。等待直到occupied为空。
				canWrite.await();
			}
			buffer = value;
			occupied = true;
			displayState("生产者写入：" + buffer);
			// 通知等待从buffer读取的任何线程。类似于notifyAll();
			canRead.signalAll();
		} finally {
			// unlock this object
			// 即使发生了异常，unlock必须执行，否则会发生死锁。
			accessLock.unlock();
		}
	}

	@Override
	public int get() throws InterruptedException {
		int readValue = 0;
		// lock this object 获得synchronizedBuffer对象的锁
		accessLock.lock();
		try {
			while (!occupied) {
				System.out.println("消费者尝试读取！");
				displayState("Buffer empty,消费者等待！");
				// 等待直到buffer不为空。
				canRead.await();
			}

			occupied = false;
			readValue = buffer;
			displayState("消费者读取：" + readValue);
			// 通知等待向buffer写入的任何线程。类似于notifyAll();
			canWrite.signalAll();
		} finally {
			// 即使发生了异常，unlock必须执行，否则会发生死锁。
			accessLock.unlock();
		}
		return readValue;
	}

	public void displayState(String operation) {
		System.out.printf("%-40s%d\t\t%b\n\n", operation, buffer, occupied);
	}
}
