package net.zhaoyu.javapros.j2se.threads.concurrentdesign.blockinginlock;

import java.util.concurrent.locks.ReentrantLock;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ReentrantLock lock=new ReentrantLock();
		for (int i=0; i<10; i++) {
			Task task=new Task(lock);
			Thread thread=new Thread(task);
			thread.start();
		}
	}

}
