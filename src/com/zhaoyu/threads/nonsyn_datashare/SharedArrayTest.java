package com.zhaoyu.threads.nonsyn_datashare;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * 创建两个线程，对一个未同步的simpleArray进行插入，
 * 一个线程插入1-3的数字，另一个线程插入11-13的数字。
 * @author xiaoe
 *
 */
public class SharedArrayTest {
	public static void main(String[] args) {
		
		//构建simpleArray
		SimpleArray sharedSimpleArray=new SimpleArray(6);
		
		ArrayWriter writer1=new ArrayWriter(1, sharedSimpleArray);
		ArrayWriter writer2=new ArrayWriter(11, sharedSimpleArray);
		
		//用ExecutorService执行线程
		ExecutorService executor=Executors.newCachedThreadPool();
		executor.execute(writer1);
		executor.execute(writer2);
		
		executor.shutdown();
		
		try{
			boolean taskEnded=executor.awaitTermination(1, TimeUnit.MINUTES);
			if (taskEnded) {
				System.out.println(sharedSimpleArray);
			}
			else {
				System.out.println("等待任务处理超时！");
			}
		}catch (InterruptedException e) {
			System.out.println("任务处理被打断");
		}
	}
	
}

/*pool-1-thread-2 在 0 写入 11
下个写入位置：1
pool-1-thread-1 在 0 写入  1
下个写入位置：2
pool-1-thread-2 在 1 写入 12
下个写入位置：3
pool-1-thread-1 在 2 写入  2
下个写入位置：4
pool-1-thread-1 在 4 写入  3
下个写入位置：5
pool-1-thread-2 在 3 写入 13
下个写入位置：6

SimpleArray的内容为:
[1, 12, 2, 13, 3, 0]*/
