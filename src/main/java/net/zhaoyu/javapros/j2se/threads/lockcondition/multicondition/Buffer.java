package net.zhaoyu.javapros.j2se.threads.lockcondition.multicondition;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者和消费者是一个典型的使用多条件的案例，ReentrantLock是轻量级锁，一个锁可以定义多个条件。通过判断条件是否成立来控制锁。
 * synchronized对比Condition，只有一个默认条件。
 * 模仿一个存储文件行的buffer，为producer和consumer服务。
 * 
 */
public class Buffer {

	private final LinkedList<String> buffer;

	/** buffer最大尺寸 */
	private final int maxSize;

	/**
	 * 访问buffer的锁
	 */
	private final ReentrantLock lock;

	/** condition:buffer是否有行需处理。*/
	private final Condition lines;

	/** condition:buffer是否有空的空间 */
	private final Condition space;

	/**
	 * 是否在向buffer中添加line
	 */
	private boolean pendingLines;


	public Buffer(int maxSize) {
		this.maxSize = maxSize;
		buffer = new LinkedList<>();
		lock = new ReentrantLock();
		lines = lock.newCondition();
		space = lock.newCondition();
		pendingLines = true;
	}

	/**
	 * 向buffer中插入一行
	 */
	public void insert(String line) {
		lock.lock();//锁定
		try {
			//判定可以插入的条件，如果不能插入，则等待。
			while (buffer.size() == maxSize) {
				space.await();
			}

			//条件允许后，插入一行。
			buffer.offer(line);
			System.out.printf("%s: 插入一行: %d\n", Thread.currentThread().getName(), buffer.size());

			//插入成功后，释放读取条件。
			lines.signalAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();//解锁
		}
	}

	/**
	 * 从buffer中返回一行
	 */
	public String get() {
		String line = null;
		lock.lock();//锁定
		try {
			//判断可以读取的条件，如果不能读取，等待。
			while ((buffer.size() == 0) && (hasPendingLines())) {
				lines.await();
			}

			//可以读取后，进行读取
			if (hasPendingLines()) {
				line = buffer.poll();
				System.out.printf("%s: 读取一行: %d\n", Thread.currentThread().getName(), buffer.size());

				//读取成功后，释放插入条件。
				space.signalAll();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();//解锁
		}
		return line;
	}

	public synchronized void setPendingLines(boolean pendingLines) {
		this.pendingLines = pendingLines;
	}

	/**
	 * 判断是否有可消费的行。
	 * @return
	 */
	public synchronized boolean hasPendingLines() {
		return pendingLines || buffer.size() > 0;
	}

}
