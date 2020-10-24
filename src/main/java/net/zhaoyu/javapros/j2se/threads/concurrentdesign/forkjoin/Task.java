package net.zhaoyu.javapros.j2se.threads.concurrentdesign.forkjoin;

import java.util.concurrent.TimeUnit;

/**
 * 任务，对一个数组的所有元素加1，
 */
public class Task implements Runnable {

	private final int array[];
	
	public Task(int array[]) {
		this.array=array;
	}
	
	@Override
	public void run() {
		for (int i=0; i<array.length; i++ ){
			array[i]++;
			try {
				TimeUnit.MILLISECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
