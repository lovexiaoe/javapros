package net.zhaoyu.javapros.j2se.threads.timer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolTest2 {
	private static long start = System.currentTimeMillis();
	private int count = 0;
	private static ScheduledExecutorService scheduler;

	private Runnable doWork=new Runnable(){@Override public void run(){try{

				System.out.println("task1 invoked ! " + (System.currentTimeMillis() - start));
				Thread.sleep(3000);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}};

	public static void main(String[] args) {
		ScheduledThreadPoolTest2 test2 = new ScheduledThreadPoolTest2();
		scheduler = Executors.newScheduledThreadPool(3);
		/**
		 * scheduleAtFixedRate每隔多少时间执行任务，如果每个任务的执行时间大于间隔时间，则等到任务完成，下一个任务才开始。
		 * 并和线程数多少没有关系。
		 */
		// scheduler.scheduleAtFixedRate(test2.doWork, 0, 1, TimeUnit.SECONDS);
		/**
		 * schedule在空闲线程时，会按照延迟执行任务。如果线程数被占用完，则等到线程释数释放，然后执行任务。
		 * 在线程数有限，且任务执行时间较长的情况下，会出现延迟。
		 */
		for (int i = 0; i <= 4; i++) {
			scheduler.schedule(test2.doWork, i, TimeUnit.SECONDS);
		}
		scheduler.shutdown();
	}
}
