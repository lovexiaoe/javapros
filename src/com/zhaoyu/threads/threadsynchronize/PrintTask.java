package com.zhaoyu.threads.threadsynchronize;
import java.util.Random;

//一个可以睡眠0-5秒的线程类。
public class PrintTask implements Runnable{
	//定义线程随机的睡眠时间
	private final int sleepTime;
	//任务的名称
	private final String taskName;
	private final static Random generator=new Random();
	
	//构造函数
	public PrintTask(String name){
		taskName=name;
		sleepTime=generator.nextInt(5000);
	}
	
	public void run(){
		try {
			System.out.printf("%s 将会睡眠 %d 毫秒。\n",taskName,sleepTime);
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			//对正在sleep的线程调用了interrupt,则sleep方法会抛出interruptedException。
			System.out.printf("%s %s\n",taskName,"由于打断过早结束");
		}
		System.out.printf("%s done sleeping\n",taskName);
	}
}
