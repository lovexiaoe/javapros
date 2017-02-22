package com.zhaoyu.threads.syn_datashare;

import java.util.Arrays;
import java.util.Random;

//这里定义一个简单的数组类，数据加同步锁。
public class SimpleArray {
	private final int[] array;
	private int writeIndex=0;
	private final static Random generator=new Random();
	
	public SimpleArray(int size){
		array=new int[size];
	}
	
	//在writeIndex位置插入值，并将writeIndex+1.
	//在多线程对一个simpleArray进行操作时，该add方法是一个原子操作，需要加同步锁。
	public synchronized void add(int value){
		int position=writeIndex;
		try {
			Thread.sleep(generator.nextInt(500));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		array[position]=value;
		System.out.printf("%s 在 %d 写入 %2d\n",Thread.currentThread().getName(),position,value);
		++writeIndex;
		System.out.printf("下个写入位置：%d\n",writeIndex);
	}
	
	@Override
	public String toString(){
		return "\nSimpleArray的内容为:\n"+Arrays.toString(array);
	}
}
