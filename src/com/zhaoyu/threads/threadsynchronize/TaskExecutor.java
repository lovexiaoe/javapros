package com.zhaoyu.threads.threadsynchronize;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TaskExecutor {
	public static void main(String[] args){
		//创建三个线程
		PrintTask task1=new PrintTask("task1");
		PrintTask task2=new PrintTask("task2");
		PrintTask task3=new PrintTask("task3");
		
		System.out.println("Starting Executor");
		
		//创建ExecutorService管理线程
		ExecutorService threadExecutor=Executors.newCachedThreadPool();
		//开启线程
		threadExecutor.execute(task1);
		threadExecutor.execute(task2);
		threadExecutor.execute(task3);
		
		//当任务完成时，关闭线程执行器,这会通知threadExecutor停止接受新任务，
		//但是会将已经提交的任务完成,只要以前提交的Runnable对象全部完成了，threadExecutor就会终止。
		threadExecutor.shutdown();
		System.out.println("开启三个任务，main结束.\n");
	}
}
