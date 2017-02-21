package com.zhaoyu.threads.nonsyn_datashare;

import java.util.Arrays;
import java.util.Random;

//���ﶨ��һ���򵥵������࣬û��ʵ��ͬ����
public class SimpleArray {
	private final int[] array;
	private int writeIndex=0;
	private final static Random generator=new Random();
	
	public SimpleArray(int size){
		array=new int[size];
	}
	
	//��writeIndexλ�ò���ֵ������writeIndex+1.
	public void add(int value){
		int position=writeIndex;
		try {
			Thread.sleep(generator.nextInt(500));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		array[position]=value;
		System.out.printf("%s �� %d д�� %2d\n",Thread.currentThread().getName(),position,value);
		++writeIndex;
		System.out.printf("�¸�д��λ�ã�%d\n",writeIndex);
	}
	
	@Override
	public String toString(){
		return "\nSimpleArray������Ϊ:\n"+Arrays.toString(array);
	}
}
