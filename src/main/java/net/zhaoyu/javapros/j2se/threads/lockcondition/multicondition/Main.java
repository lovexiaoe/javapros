package net.zhaoyu.javapros.j2se.threads.lockcondition.multicondition;


public class Main {

	public static void main(String[] args) {

		FileMock mock = new FileMock(101, 10);
		Buffer buffer = new Buffer(20);

		/** 创建一个生产者 */
		Producer producer = new Producer(mock, buffer);
		Thread producerThread = new Thread(producer, "Producer");

		/** 创建3个消费者 */
		Consumer consumers[] = new Consumer[3];
		Thread consumersThreads[] = new Thread[3];
		for (int i = 0; i < 3; i++) {
			consumers[i] = new Consumer(buffer);
			consumersThreads[i] = new Thread(consumers[i], "Consumer " + i);
		}

		/** 启动生产者和消费者 */
		producerThread.start();
		for (int i = 0; i < 3; i++) {
			consumersThreads[i].start();
		}
	}

}
