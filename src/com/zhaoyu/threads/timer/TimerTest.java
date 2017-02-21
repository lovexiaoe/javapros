package com.zhaoyu.threads.timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 1,Timer������ʱ�����ȱ��
 * a����ǰ����Ŀ��Ҳ����ʹ�ö�ʱ��������ÿ��һ��ʱ��������Ŀ�е�һЩ�����ļ���ÿ��һ��ʱ�����������ϴ��Ȼ��Timer�Ǵ���һЩȱ�ݵģ�
 * ��ΪTimer��ִ�ж�ʱ����ʱֻ�ᴴ��һ���̣߳� ����������ڶ������������ʱ���������������������ļ��ʱ�䣬�ᷢ��һЩȱ��
 * 
 * 2��Timer�������׳��쳣ʱ��ȱ��,���TimerTask�׳�RuntimeException��Timer��ֹͣ������������У�
 * 
 * 3��Timerִ����������ʱ����ϵͳʱ��,Timerִ����������ʱ����ϵͳʱ�䣬�����ǰϵͳʱ�䷢���仯�����һЩִ���ϵı仯��
 * ScheduledExecutorService����ʱ����ӳ٣���������ϵͳʱ��ĸı䷢��ִ�б仯��
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

	// ��������TimerTask��ʱ����
	public static void test1() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				System.out.println(new Date());
				System.out.println("-------�趨Ҫָ������--------");
			}
		}, 1000);
	}
}
