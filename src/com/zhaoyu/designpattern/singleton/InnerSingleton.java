package com.zhaoyu.designpattern.singleton;


/**
 * ʹ���ڲ���Ҳ���Դﵽ�ӳټ��ص�����Ŀ�ģ��Ҷ��߳��Ѻá� InnerSingleton
 * �ڼ���ʱ���ڲ��಻�ᱻ��ʼ��������ȷ��InnerSingleton������JVMʱ�������ʼ��instance��
 * ��instance�Ĵ������������ʱ��ɵģ����������Զ��߳��Ѻá�
 * 
 * @author dmall223
 *
 */
public class InnerSingleton {
	private InnerSingleton() {
		System.out.println("InnerSingleton is created!");
	}

	private static class SingletonHolder {
		private static InnerSingleton instance = new InnerSingleton();
	}

	public static InnerSingleton getInstance() {
		return SingletonHolder.instance;
	}

	public static void main(String[] args) {
		getInstance();
		getInstance();

	}
}
