package net.zhaoyu.javapros.j2se.threads.ProducerConsumer.loopbuffer_producer_cosumer;

/**
 * 这里我们自己通过wait、notify、nofityAll来实现循环缓冲区的线程同步 共享缓冲区，生产者在数组后面进行写入，消费者在前面进行读取。
 * 这种类似于队列的数据结构为循环的数组组成的循环队列，生产者按照顺序写，消费者 按照顺序读，当计数为0时，消费者读完所有数据。
 * 
 * @author xiaoe
 * 
 */
public class synchronizedBuffer implements Buffer {

	private final int[] buffer = { -1, -1, -1 };

	// 缓冲区计数器
	private int count;
	// 下一个写入元素的index
	private int writeIndex = 0;
	// 下一个读取元素的index
	private int readIndex = 0;

	@Override
	public synchronized int get() throws InterruptedException {
		while (count == 0) {
			// System.out.println("队列缓冲区是空了！");
			wait();
		}

		int readValue = buffer[readIndex];
		System.out.println("消费者消费了在  " + readIndex + " 的  " + readValue);
		count--;
		readIndex = (readIndex + 1) % buffer.length;
		notifyAll();
		return readValue;
	}

	@Override
	public synchronized void set(int value) throws InterruptedException {
		while (count == buffer.length) {
			// System.out.println("队列缓冲区写满了");
			wait();
		}
		buffer[writeIndex] = value;
		System.out.println("生产者在  " + writeIndex + " 写入  " + value);
		count++;
		writeIndex = (writeIndex + 1) % buffer.length;
		notifyAll();
	}

}
