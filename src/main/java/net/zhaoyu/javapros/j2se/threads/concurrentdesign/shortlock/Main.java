package net.zhaoyu.javapros.j2se.threads.concurrentdesign.shortlock;

import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁和其他同步机制一样，定义了一个临界区，在使用锁时，应该只包括互斥代码。
 * 输出如下，可以看到短锁性能是长锁的3倍：
 * Main: First Approach: 30016
 * Main: Second Approach: 10011
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Lock lock=new ReentrantLock();
		Task1 task1=new Task1(lock);
		Task2 task2=new Task2(lock);
		Thread threads[]=new Thread[10];
		
		Date begin, end;
		
		begin=new Date();
		for (int i=0; i<threads.length; i++) {
			threads[i]=new Thread(task1);
			threads[i].start();
		}
		
		for (int i=0; i<threads.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		end=new Date();
		System.out.printf("Main: First Approach: %d\n",(end.getTime()-begin.getTime()));
		
		begin=new Date();
		for (int i=0; i<threads.length; i++) {
			threads[i]=new Thread(task2);
			threads[i].start();
		}
		
		for (int i=0; i<threads.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		end=new Date();
		System.out.printf("Main: Second Approach: %d\n",(end.getTime()-begin.getTime()));
		

	}

}
