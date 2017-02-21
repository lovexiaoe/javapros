package com.zhaoyu.threads.threadsynchronize;
import java.util.Random;

//һ������˯��0-5����߳��ࡣ
public class PrintTask implements Runnable{
	//�����߳������˯��ʱ��
	private final int sleepTime;
	//���������
	private final String taskName;
	private final static Random generator=new Random();
	
	//���캯��
	public PrintTask(String name){
		taskName=name;
		sleepTime=generator.nextInt(5000);
	}
	
	public void run(){
		try {
			System.out.printf("%s ����˯�� %d ���롣\n",taskName,sleepTime);
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			//������sleep���̵߳�����interrupt,��sleep�������׳�interruptedException��
			System.out.printf("%s %s\n",taskName,"���ڴ�Ϲ������");
		}
		System.out.printf("%s done sleeping\n",taskName);
	}
}
