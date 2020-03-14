package net.zhaoyu.javapros.j2se.threads.ProducerConsumer.syn_producer_cosumer;

/**
 * 这里我们自己通过wait、notify、nofityAll来实现有界缓冲区的线程同步
 * 共享缓冲区，生产者在数组后面进行写入，消费者在前面进行读取。
 * 这种类似于队列的数据结构为不循环的数组组成的队列，生产者只能在高位写，而消费者
 * 只能在低位读，当读写一样且等于最高位时复位为最低位。
 * @author xiaoe
 *
 */
public class synchronizedBuffer implements Buffer{

	private final int[] buffer={-1,-1,-1};
	
	//下一个写入元素的index
	private int writeIndex=0;
	//下一个读取元素的index
	private int readIndex=0;

	@Override
	public synchronized int get() throws InterruptedException {
		while (readIndex==writeIndex) {
			//System.out.println("队列缓冲区是空了！");
			if (readIndex==buffer.length) {
				//当队列消费完最顶部的元素时，重置读写索引，并notify生产者。
				readIndex=writeIndex=0;
				notifyAll();
			}else {
				wait();
			}
			
		}
		
		int readValue=buffer[readIndex];
		System.out.println("消费者消费了在  "+readIndex+" 的  "+readValue);

		readIndex++;
		notifyAll();
		return readValue;
	}

	@Override
	public synchronized void set(int value) throws InterruptedException {
		while (writeIndex>=buffer.length) {
			//System.out.println("队列缓冲区写满了");
			wait();
		}
		buffer[writeIndex]=value;
		System.out.println("生产者在  "+writeIndex+" 写入  "+value);
		writeIndex++;
		notifyAll();
	}
	
}
