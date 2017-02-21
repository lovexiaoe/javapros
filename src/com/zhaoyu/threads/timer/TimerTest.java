package com.zhaoyu.threads.timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 1,Timer管理延时任务的缺陷
 * a、以前在项目中也经常使用定时器，比如每隔一段时间清理项目中的一些垃圾文件，每个一段时间进行数据清洗；然而Timer是存在一些缺陷的，
 * 因为Timer在执行定时任务时只会创建一个线程， 所以如果存在多个任务，且任务时间过长，超过了两个任务的间隔时间，会发生一些缺陷
 * 
 * 2、Timer当任务抛出异常时的缺陷,如果TimerTask抛出RuntimeException，Timer会停止所有任务的运行：
 * 
 * 3、Timer执行周期任务时依赖系统时间,Timer执行周期任务时依赖系统时间，如果当前系统时间发生变化会出现一些执行上的变化，
 * ScheduledExecutorService基于时间的延迟，不会由于系统时间的改变发生执行变化。
 * 
 * @author dmall223
 *
 */
public class TimerTest {
	private static long start;

	public static void main(String[] args) throws Exception {

		TimerTask task1 = new TimerTask() {
			@Override
			public void run() {

				System.out.println("task1 invoked ! " + (System.currentTimeMillis() - start));
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		};
		TimerTask task2 = new TimerTask() {
			@Override
			public void run() {
				System.out.println("task2 invoked ! " + (System.currentTimeMillis() - start));
			}
		};
		Timer timer = new Timer();
		start = System.currentTimeMillis();
		timer.schedule(task1, 1000);
		timer.schedule(task2, 3000);

	}

	// 匿名创建TimerTask定时任务。
	public static void test1() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				System.out.println(new Date());
				System.out.println("-------设定要指定任务--------");
			}
		}, 1000);
	}
}
