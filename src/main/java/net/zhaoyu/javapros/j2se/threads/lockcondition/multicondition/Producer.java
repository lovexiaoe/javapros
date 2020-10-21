package net.zhaoyu.javapros.j2se.threads.lockcondition.multicondition;


public class Producer implements Runnable {

	private FileMock mock;

	private Buffer buffer;

	public Producer(FileMock mock, Buffer buffer) {
		this.mock = mock;
		this.buffer = buffer;
	}

	/**
	 * 从文本中读取行，写入到buffer中。
	 */
	@Override
	public void run() {
		buffer.setPendingLines(true);
		while (mock.hasMoreLines()) {
			String line = mock.getLine();
			buffer.insert(line);
		}
		buffer.setPendingLines(false);
	}

}
