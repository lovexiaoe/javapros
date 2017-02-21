package com.zhaoyu.threads.timer;

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
		 * scheduleAtFixedRateÿ������ʱ��ִ���������ÿ�������ִ��ʱ����ڼ��ʱ�䣬��ȵ�������ɣ���һ������ſ�ʼ��
		 * �����߳�������û�й�ϵ��
		 */
		// scheduler.scheduleAtFixedRate(test2.doWork, 0, 1, TimeUnit.SECONDS);
		/**
		 * schedule�ڿ����߳�ʱ���ᰴ���ӳ�ִ����������߳�����ռ���꣬��ȵ��߳������ͷţ�Ȼ��ִ������
		 * ���߳������ޣ�������ִ��ʱ��ϳ�������£�������ӳ١�
		 */
		for (int i = 0; i <= 4; i++) {
			scheduler.schedule(test2.doWork, i, TimeUnit.SECONDS);
		}
		scheduler.shutdown();
	}
}
