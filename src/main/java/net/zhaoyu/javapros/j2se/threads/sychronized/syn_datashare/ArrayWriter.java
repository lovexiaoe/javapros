package net.zhaoyu.javapros.j2se.threads.sychronized.syn_datashare;

public class ArrayWriter implements Runnable{
	private final SimpleArray sharedSimpleArray;
	private final int startValue;
	
	//构造方法
	public ArrayWriter(int value,SimpleArray array){
		startValue=value;
		sharedSimpleArray=array;
	}
	//线程执行方法，插入startvalue到startvalue+3的数据。
	public void run(){
		for (int i = startValue; i < startValue+3; i++) {
			sharedSimpleArray.add(i);
		}
	}
}
