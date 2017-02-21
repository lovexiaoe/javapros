package com.zhaoyu.threads.syn_datashare;

public class ArrayWriter implements Runnable{
	private final SimpleArray sharedSimpleArray;
	private final int startValue;
	
	//���췽��
	public ArrayWriter(int value,SimpleArray array){
		startValue=value;
		sharedSimpleArray=array;
	}
	//�߳�ִ�з���������startvalue��startvalue+3�����ݡ�
	public void run(){
		for (int i = startValue; i < startValue+3; i++) {
			sharedSimpleArray.add(i);
		}
	}
}
