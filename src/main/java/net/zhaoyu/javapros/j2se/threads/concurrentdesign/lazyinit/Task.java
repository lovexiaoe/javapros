package net.zhaoyu.javapros.j2se.threads.concurrentdesign.lazyinit;

public class Task implements Runnable {

	@Override
	public void run() {

		System.out.printf("%s: Getting the connection...\n",Thread.currentThread().getName());
		DBConnectionOK connection=DBConnectionOK.getConnection();
		System.out.printf("%s: End,connection is %s: End\n",Thread.currentThread().getName(),connection);
	}

}
