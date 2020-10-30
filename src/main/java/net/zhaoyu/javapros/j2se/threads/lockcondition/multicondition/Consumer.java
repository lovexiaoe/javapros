package net.zhaoyu.javapros.j2se.threads.lockcondition.multicondition;

import java.util.Random;


public class Consumer implements Runnable {

	private Buffer buffer;

	public Consumer(Buffer buffer) {
		this.buffer = buffer;
	}

	/**
	 * 读取buffer中的行，并处理
	 */
	@Override
	public void run() {
		while (buffer.hasPendingLines()) {
			String line = buffer.get();
			processLine(line);
		}
	}

	/**
	 * 处理一行
	 */
	private void processLine(String line) {
		try {
			Random random = new Random();
			Thread.sleep(random.nextInt(100));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
