package com.zhaoyu.threads.nonsyn_datashare;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * ���������̣߳���һ��δͬ����simpleArray���в��룬
 * һ���̲߳���1-3�����֣���һ���̲߳���11-13�����֡�
 * @author xiaoe
 *
 */
public class SharedArrayTest {
	public static void main(String[] args) {
		
		//����simpleArray
		SimpleArray sharedSimpleArray=new SimpleArray(6);
		
		ArrayWriter writer1=new ArrayWriter(1, sharedSimpleArray);
		ArrayWriter writer2=new ArrayWriter(11, sharedSimpleArray);
		
		//��ExecutorServiceִ���߳�
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
				System.out.println("�ȴ�������ʱ��");
			}
		}catch (InterruptedException e) {
			System.out.println("���������");
		}
	}
	
}

/*pool-1-thread-2 �� 0 д�� 11
�¸�д��λ�ã�1
pool-1-thread-1 �� 0 д��  1
�¸�д��λ�ã�2
pool-1-thread-2 �� 1 д�� 12
�¸�д��λ�ã�3
pool-1-thread-1 �� 2 д��  2
�¸�д��λ�ã�4
pool-1-thread-1 �� 4 д��  3
�¸�д��λ�ã�5
pool-1-thread-2 �� 3 д�� 13
�¸�д��λ�ã�6

SimpleArray������Ϊ:
[1, 12, 2, 13, 3, 0]*/
