package net.zhaoyu.javapros.j2se.threads.concurrentdesign.executor;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * executor的主要优势：
 * 1.创建线程减少，不需要为所有任务创建线程。减少内存等资源消耗。
 * 2.既可以运行runnable，也可以运行callable。
 * 3.可以规划和控制任务的执行，如运行时间，数量等。
 *
 * thread 和使用executor的性能对比：
 * Main: Threads: 70
 * Main: Executor: 18
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Thread threads[]=new Thread[1000];
		Date start,end;
		
		start=new Date();
		for (int i=0; i<threads.length; i++) {
			Task task=new Task();
			threads[i]=new Thread(task);
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
		System.out.printf("Main: Threads: %d\n",(end.getTime()-start.getTime()));
		
		ThreadPoolExecutor executor=(ThreadPoolExecutor)Executors.newCachedThreadPool();
		
		start=new Date();
		for (int i=0; i<threads.length; i++) {
			Task task=new Task();
			executor.execute(task);
		}
		executor.shutdown();
		try {
			executor.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		end=new Date();
		System.out.printf("Main: Executor: %d\n",(end.getTime()-start.getTime()));

	}

}
